# vending-machine-noser

This is API with functionality of a Vending Machine. 
Java 20 and
MySql DataBase

Instructions on how to use the API:

# POST /api/product-category/
1. First create some product categories. Sample json:
  {
    "type": "sweets"
  }
# POST /api/product/ <br>
2. Then create some products. Sample json:<br>
   {
    "name": "Lays",
    "description": "description",
    "price": 3.80,
    "categoryId": "39e93f99-3896-480a-91f7-7d17dc89bcab"
  }
  
  # POST /api/vending-machine/ <br>
3. Then create the vending machine. Sample json:<br>
  {
  "productIds": ["148535d2-1bf5-4ede-b094-84bd0d494662", "2f8d155c-c6df-435b-8be2-6531636a5853", "5ec0a1a0-4a69-42ad-a233-c452ab52d89e"],
  "paymentItems": [0.10, 0.20, 0.50, 1.0, 2.0]
  }
  
  # POST /api/vending-machine/load<br>
4. Additional money/coins can be inserted as stored money of the vending machine. Sample json:<br>
  {
  "tenStQuantity": 9,
  "twentyStQuantity": 9,
  "fiftyStQuantity": 9,
  "levQuantity": 9,
  "twoLevQuantity": 9
  }
  
  # POST /api/vending-machine/insert-money<br>
5. Then the "user" must insert coins in order to make a purchase. Sample json:<br>
  {
    "value": 2.0
  }
  
# 6. Buy a product through /api/vending-machine/by/{productId}
