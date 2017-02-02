(ns wombats
  (:require [uswitch.lambada.core :refer [deflambdafn]]
            [clojure.data.json :as json]
            [clojure.java.io :as io]))

(defn handle-event
  [event time-left]
  (let [arena (get event "state")
        code-string (get event "code")]

    ;; Call the wombat's code, passing in appropriate args
    ((load-string code-string) time-left arena)))

(deflambdafn wombats.Handler
  [in out ctx]
  (let [time-left (fn [] (.getRemainingTimeInMillis ctx))
        event (json/read (io/reader in))
        res (handle-event event time-left)]

    (with-open [w (io/writer out)]
      (json/write res w))))