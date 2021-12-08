# Redundancy

order_room.config <> order_room.room.config  
    to keep config when order was generated

room.hotel <> room.config.hotel
    to avoid get hotel from room via config
    hotel_id will not be updated

room_unit.price <> room_unit.room_config.def_price
    to allow different price for different days
    when def_price changes, all units' prices need update

hotel.min_price <> COMPUTED BY min(room.price)
    brief of hotel needs min price which is not frequently changed
    the field will be updated per hour
