## Changes between 1.0.2 and HEAD

## Changes between 1.0.1 and 1.0.2

* Added service type IDs for ballot mail origin tracing: `777`, `778`, `779`.

## Changes between 1.0.0 and 1.0.1

* Added a new `imbarcode.core/encodable?` predicate fn that takes all the same
  arguments (including the 3 different arities) as `imbarcode.core/encode` and
  returns true if they can be encoded into an IMb or false otherwise. This is
  much faster than doing the actual encoding if all you're interested in is
  whether or not you have enough / valid input data to encode an IMb.

## Changes between 0.1.6 and 1.0.0

* Project renamed from `turbovote.imbarcode` to `democracyworks/imbarcode`
    * This moved all of the files, directories, and namespaces around
    * **This is a breaking change**
* Ported from cljx to reader conditionals (cljc)
* ClojureScript tests ported from `com.cemerick/clojurescript.test` to `cljs.test`

## Changes prior to 0.1.6

Changes.md was added at version 1.0.0 so ¯\\\_(ツ)_/¯
