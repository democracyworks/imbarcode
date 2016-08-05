(ns turbovote.imbarcode.test-runner
  (:require [doo.runner :refer-macros [doo-all-tests]]
            [turbovote.imbarcode-test]
            [turbovote.imbarcode.bars-test]
            [turbovote.imbarcode.binary-test]
            [turbovote.imbarcode.characters-test]
            [turbovote.imbarcode.codewords-test]
            [turbovote.imbarcode.crc-test]))

(doo-all-tests #"^turbovote\.imbarcode.*-test$")
