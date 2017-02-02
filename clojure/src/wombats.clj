(ns wombats
  (:require [uswitch.lambada.core :refer [deflambdafn]]
            [clojure.data.json :as json]
            [clojure.java.io :as io]
            [base64-clj.core :as b64]))

(defn- decode-bot
  [content]
  (let [base64-string (clojure.string/replace content #"\n" "")]
    (b64/decode base64-string "UTF-8")))

(defn handle-event
  [event time-left]
  (let [state (get event "state")
        code-string (decode-bot (get event "code"))]
    ;; Call the wombat's code, passing in appropriate args
    (prn code-string)
    (try
      ((load-string code-string) state) ;; add time-left later
      (catch Exception e))))

(deflambdafn wombats.Handler
  [in out ctx]
  (let [time-left (fn [] (.getRemainingTimeInMillis ctx))
        event (json/read (io/reader in))
        res (handle-event event time-left)]

    (with-open [w (io/writer out)]
      (json/write res w))))