/*
 *
 *  Copyright 2019 Hedera Hashgraph LLC
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package hedera.hgc.hgcwallet.modals

import org.json.JSONObject
import java.lang.Exception

data class ExchangeRateInfo constructor(val exchange: Exchange, val lastUpdated: Long? = null, var last: Double? = null, var bid: Double? = null, var ask: Double? = null) {
    companion object {
        fun createExchangeRateInfo(exchange: Exchange, json: JSONObject, date: Long? = null): ExchangeRateInfo {
            return when (exchange) {
                Exchange.Bitrex -> {
                    try {
                        var last: Double? = null
                        var bid: Double? = null
                        var ask: Double? = null

                        json.optJSONObject("result")?.let { obj ->
                            last = obj.optDouble("Last", 0.0).let { if (it > 0.0) it else null }
                            bid = obj.optDouble("Bid", 0.0).let { if (it > 0.0) it else null }
                            ask = obj.optDouble("Ask", 0.0).let { if (it > 0.0) it else null }
                        }

                        ExchangeRateInfo(exchange, date, last, bid, ask)
                    } catch (e: Exception) {
                        ExchangeRateInfo(exchange, date)
                    }
                }
                Exchange.Okcoin -> {
                    try {
                        val last = json.optDouble("last", 0.0).let { if (it > 0.0) it else null }
                        val bid = json.optDouble("bid", 0.0).let { if (it > 0.0) it else null }
                        val ask = json.optDouble("ask", 0.0).let { if (it > 0.0) it else null }


                        ExchangeRateInfo(exchange, date, last, bid, ask)
                    } catch (e: Exception) {
                        ExchangeRateInfo(exchange, date)
                    }
                }
                Exchange.Liquid -> {
                    try {
                        val last = json.optDouble("last_traded_price", 0.0).let { if (it > 0.0) it else null }
                        val bid = json.optDouble("market_bid", 0.0).let { if (it > 0.0) it else null }
                        val ask = json.optDouble("market_ask", 0.0).let { if (it > 0.0) it else null }


                        ExchangeRateInfo(exchange, date, last, bid, ask)
                    } catch (e: Exception) {
                        ExchangeRateInfo(exchange, date)
                    }
                }
            }
        }
    }


}

enum class Exchange(val value: String) {
    Bitrex("bitrex"),
    Okcoin("okcoin"),
    Liquid("liquid")

}