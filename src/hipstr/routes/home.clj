 (ns hipstr.routes.home
  (:require [compojure.core :refer :all]
            [hipstr.layout :as layout]
            [hipstr.validators.user-validator :as v]
            [ring.util.response :as response]
            [taoensso.timbre :as log]
            [hipstr.util :as util]))

(defn home-page []
  (layout/render
    "home.html" {:content (util/md->html "/md/docs.md")}))

(defn about-page []
  (layout/render "about.html"))

(defn signup-page-submit [user]
  (let [errors (v/validate-signup user)]
    (log/fatal user)
  (if (empty? errors)
    (response/redirect "/signup-success")
    (layout/render "signup.html" (assoc user :errors errors)))
  ))


(defn signup-page []
  (layout/render "signup.html"))

(defn foo-response [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str "<html><body><h1>Go bowling?</h1><h3>" (:go-bowling request) "</h3></body></html>")
   })

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" request (foo-response request))
  (GET "/signup" [] (signup-page))
  (POST "/signup" [& form] (signup-page-submit form))
  (GET "/signup-success" [] "Success!")
   )
