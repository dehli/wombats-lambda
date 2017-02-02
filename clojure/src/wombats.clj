(ns wombats
  (:require [uswitch.lambada.core :refer [deflambdafn]]
            [clojure.data.json :as json]
            [clojure.java.io :as io]))

(defn handle-event
  [event time-left]
  (let [state (get event "state")
        code-string (get event "code")]
    ;; Call the wombat's code, passing in appropriate args
    (try
      ((load-string code-string) state time-left)
      (catch Exception e))))

(deflambdafn wombats.Handler
  [in out ctx]
  (let [time-left (fn [] (.getRemainingTimeInMillis ctx))
        event (json/read (io/reader in))
        res (handle-event event time-left)]

    (with-open [w (io/writer out)]
      (json/write res w))))
