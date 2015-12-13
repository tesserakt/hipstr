(ns hipstr.routes.test-routes
  (:require [compojure.core :refer :all]))

  (defn render-request-val [request-map & [request-key]]
    "Simply returns the value of request-key in request-map,
     if request-key is provided; Otherwise return the request-map.
     If request-key is provided, but not found in the request-map,
     a message indicating as such will be returned."
    (str (if request-key
           (if-let [result ((keyword request-key) request-map)]
             result
             (str request-key "is not a valid key"))
           request-map))

    )

  (defroutes test-routes
    (POST "/req" request (render-request-val request))
    (ANY "/req" request (str request))
    (GET "/req/:val" [val more :as request & remainders] (str val "<br />" more "<br />" request "<br />" "<br />" "<br />" remainders))
    (GET "/req/key/:key" [key :as request] (render-request-val request key))
             )