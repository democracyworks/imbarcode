# turbovote.imbarcode

A Clojure/ClojureScript library designed to generate USPS Intelligent Mail Barcodes.
The core functionality is in turbovote.imbarcode/encode. Given the necessary parameters,
this will output a string representing the extenders in the computed barcode.

See https://ribbs.usps.gov/onecodesolution for a complete specification
of USPS Intelligent Mail Barcodes, a font file to properly display your encoded barcode,
and other useful resources.


## Installation

`imbarcode` is available as a Maven artifact from
[Clojars](http://clojars.org/turbovote.imbarcode):
```clojure
[turbovote.imbarcode "0.1.0"]
```

## Usage

The top-level encoding functionality is provided in the
`turbovote.imbarcode` namespace.

Require it in the REPL:

```clojure
(require '[turbovote.imbarcode :as imb])
```

Require it in your application:

```clojure
(ns my-app.core
  (:require [turbovote.imbarcode :as imb]))
```

```clojure
user=> (imb/encode "00" "040" "123456789" "123456" "123456789")
"ADFFDTDATDTDDDTTDTTFTFFAFTATFATATDATDDDDDFDAFAFFATTADDTATDDDAFFFA"
user=> (imb/encode "00" "050" "999888777666555" "012345678")
"FAADADDDTAFTAFDFFTDAADADTAFTTDATAAFTAFTTFAATAAFTFATDTFATFFTATTAAF"
```

See ```resources/public/dev-index.html``` and ```resources/public/index.html``` for examples
of usage in ClojureScript


## Development

To run all the tests (Clojure and ClojureScript) on a clean build:

    $ lein cleantest

To run just the Clojure tests:

    $ lein test

To run just the ClojureScript tests:

    $ lein cljsbuild test

Note that we make use of cljx so you may need to manually run `lein cljx` before
running just the ClojureScript tests.

## License

Copyright (C) 2014 Democracy Works

Distributed under the Eclipse Public License, the same as Clojure.
