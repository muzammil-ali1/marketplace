# Crypto Marketplace project

Place a new order.    
URI: "/order" 
METHOD: POST  
PAYLOAD: {"userID":10,"coinType":"Ethereum","orderQty":10.0,"pricePerCoin":4.5,"transactionType":"BUY"}

Cancel Order.  
URI: "/crypto/cancel/{id}"  
METHOD: DELETE. 


Show Summary Board      
URI : "/crypto/show/{transactionType}"  
METHOD: GET  
