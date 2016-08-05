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
[turbovote.imbarcode "0.1.2"]
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
user=> ; Destination tracing
user=> (imb/encode imb/barcode-id:default imb/service-type-id:destination "123456789" "123456" "123456789")
"ADFFDTDATDTDDDTTDTTFTFFAFTATFATATDATDDDDDFDAFAFFATTADDTATDDDAFFFA"
user=> ; Origin tracing
user=> (imb/encode imb/barcode-id:default imb/service-type-id:origin "999888777666555" "012345678")
"FAADADDDTAFTAFDFFTDAADADTAFTTDATAAFTAFTTFAATAAFTFATDTFATFFTATTAAF"
```

See ```resources/public/dev-index.html``` and ```resources/public/index.html``` for examples
of usage in ClojureScript


## Development

To run all the tests (Clojure and ClojureScript):

```shell
lein test
```

To run just the Clojure tests:

```shell
lein clj-test
```

To run just the ClojureScript tests:

```shell
lein cljs-test
```

## License

Copyright (C) 2014 Democracy Works

Distributed under the Eclipse Public License, the same as Clojure.
