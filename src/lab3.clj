(ns lab3
  (:require [metrics-server.api.hardware :refer [get-metrics]])
  (:require [metrics-server.api.files :refer [get-files]]))


;; Среднее арифметическое
(defn average
      [numbers]
      (/ (apply + numbers) (count numbers)))

;; Фильтрация директорий
(defn non-dirs
  [lst]
  (for [f lst
        :when (not (:directory f))]
     f))

;; Список всех исполняемых файлов
(defn execs
  [lst]
  (for [f lst
        :when (:executable f)]
    f))

;; Смена расширения файла .conf -> .cfg
(defn change-ext
  [file]
  (if (re-matches #".+\.conf$" (:name file))
    (merge {:name (str (re-find #".+\." (:name file)) "cfg")} (dissoc file :name))
    file))


;; Часть 1. hardware/metrics
(def metrics (get-metrics))

(println "Часть 1. Работа с metrics")
(println "Средняя температура:" (average (map :cpuTemp metrics)))
(println "Средняя загрузка:" (average (map :cpuLoad metrics)))

(println (repeat 50 "-"))
(println (repeat 50 "-"))

;; Часть 2. hardware/files
(def all_files (get-files))
(def files (non-dirs all_files))

(println "Часть 2. Работа с files")
(println "№1. Список без директорий:")
(clojure.pprint/pprint files)

(println (repeat 50 "-"))

(println "№2. Исполняемые файлы:")
(clojure.pprint/pprint (execs files))

(println (repeat 50 "-"))

(println "№3. Смена расширения:")
(clojure.pprint/pprint (map change-ext files))

(println (repeat 50 "-"))

(println "№4. Средний размер файлов:" (average (map :size files)))