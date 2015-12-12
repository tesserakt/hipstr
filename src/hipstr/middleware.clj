(ns hipstr.middleware
  (:require [taoensso.timbre :as timbre]
            [selmer.parser :as parser]
            [environ.core :refer [env]]
            [selmer.middleware :refer [wrap-error-page]]
            [prone.middleware :refer [wrap-exceptions]]
            [noir-exception.core :refer [wrap-internal-error]]))

(defn go-bowling? [handler]
  (fn [request]
    (let [request (assoc request :go-bowling "Yes! NOW!")]
      (handler request)))
  )

(defn log-request [handler]
  (fn [req]
    (timbre/debug req)
    (handler req)))

(def development-middleware
  [
   go-bowling?
   wrap-error-page
   wrap-exceptions])

(def production-middleware
  [#(wrap-internal-error % :log (fn [e] (timbre/error e)))])

(defn load-middleware []
  (concat (when (env :dev) development-middleware)
          production-middleware))
