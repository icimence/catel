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

room_config.room_number <> COMPUTED by count(room)
    avoid to re-calculate room number per day when create new room unit

user.credit <> order.credit_delta <> COMPUTED by count(credit_entry)
    avoid to re-calculate credit per order check
    when credit_entry update, user's and order's credit need updates

