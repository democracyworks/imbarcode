(ns imbarcode.test-runner
  (:require [doo.runner :refer-macros [doo-all-tests]]
            [imbarcode.core-test]
            [imbarcode.bars-test]
            [imbarcode.binary-test]
            [imbarcode.characters-test]
            [imbarcode.codewords-test]
            [imbarcode.crc-test]))

(doo-all-tests #"^imbarcode\..*-test$")
