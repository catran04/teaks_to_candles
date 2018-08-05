# Getting started

It's a simple application that receives the teaks from some stockExchange makes the minute candles and can send to client these candles using webSocket.

# For launch

## application of options
  - you can apply options by default:
  - you can custom options;

### Options by default
- if options by default when using `SQLiteTeakDao`.
- host of websocket - `localhost`
- port of websocket - `9590`

### Custom options
you should apply the options to argument of app in format
`<nameOption1>=<value> <nameOption2=<value>`

#### supported options
|   argument | definition        |       default           |
| --- | ---|---|
|`storage=<String>`| what storage will be used. Allows `SqLite` only yet|  `SqLite`|
| `teakReceiverHost=<String>` | host of the client that receives the data of teaks from remote exchange.  | `localhost`|
| `teakReceiverPort=<Integer>` | port of the client that receives the data of teaks from remote exchange.  |`5555`|
| `serverHost=<String>`|a host of the server| `localhost`|
| `serverPort=<Integer>` |a port of the server.  |`9590`|
| `clientHost=<String>` |a host of the client.  | `localhost`|
| `clientPort=<Integer>` |a port of the client. | `9590`|

## Launch using sbt
### First step
- install sbt
### Second step
- clone this repository
### Third step
- enter in a console command in source folder
`sbt "runMain com.catran.trading.netty.teaker.TeakerGetter` or `sbt "runMain com.catran.trading.netty.teaker.TeakerGetter <nameOption1>=<value> <nameOption2=<value>"` for applying custom options. This command runs application that will receive the teaks from exchange.
### Fourth step
- enter in a console command in source folder `sbt "runMain com.catran.trading.netty.server.Server` or with options as above. This command runs Server application that will be receive teakers from a storage and aggregate their to candles and send to an user.
### Fifth step
- enter in a console command in source folder `sbt "runMain com.catran.trading.netty.client.Client` or with options as above. This command runs the client that will be receive candles.

## State store
- By default the application uses `SqLite` database.
- In future will be implemented Kafka.