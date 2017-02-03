(ns wombats
  (:require [uswitch.lambada.core :refer [deflambdafn]]
            [cheshire.core :as cheshire]
            [clojure.java.io :as io]))

(defn handle-event
  [event time-left]
  ;; Call the passed in code with the appropriate args
  (try
    (let [user-defined-code (load-string (:code event))
          state (:state event)
          user-response (user-defined-code state time-left)]
      {:response user-response
       :error nil})
    (catch Exception e
      {:response nil
       :error e})))

(deflambdafn wombats.Handler
  [in out ctx]
  (let [time-left (fn [] (.getRemainingTimeInMillis ctx))
        event (cheshire/parse-stream (io/reader in) true)
        res (handle-event event time-left)]

    (with-open [w (io/writer out)]
      (cheshire/generate-stream res w))))
