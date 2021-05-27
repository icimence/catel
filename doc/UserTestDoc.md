# 测试文档 - 客户相关

[TOC]

## 1. 单元测试

### 1.1 用户界面层单元测试

#### 1.1.1 页面显示与跳转测试
```
   - 首页
      - 酒店详情界面
         - 预定酒店模态框
   - 个人中心界面
      - 个人信息管理
         - 修改信息模态框
         - 修改密码模态框
         - 开通会员模态框
      - 入住人信息管理
         - 查看信息模态框
         - 修改信息模态框
         - 删除信息模态框
         - 添加用户模态框
      - 预定订单信息管理
         - 查看订单模态框
         - 撤销订单模态框
         - 评论订单模态框

   测试结果：通过
```
#### 1.1.2 功能测试

##### 1.1.2.1 向业务层请求查看酒店列表

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|访问首页界面|无|浏览器向服务器发起 /api/hotel/all GET请求|通过|

##### 1.1.2.2 向业务层请求查看酒店详情信息

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|点击酒店列表中的酒店|hotelId|浏览器向服务器发起 /api/hotel/{hotelId}/detail GET请求|通过|

##### 1.1.2.3 收集并向业务层请求添加酒店预定订单信息

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|点击“预定”按钮|客户id|浏览器向服务器发起 /api/person/by-user-id GET请求|通过|
|点击“下单”按钮|收集的表单信息|浏览器向服务器发起 /api/order/addOrder POST请求|通过|
|预定信息满足优惠券要求|收集的表单信息|浏览器向服务器发起 /api/coupon/orderMatchCoupons POST请求|通过|

测试数据：
       - 入住人身份：person1
       - 入住日期：2020-06-19 ~ 2020-06-30
       - 入住人数：2
       - 有无儿童：无
       - 房间数：1

##### 1.1.2.4 向业务层请求查看用户评论

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|点击酒店详情页面的“用户评论”选项|酒店id|浏览器向服务器发起 /api/comment/by-hotel GET请求|通过|

##### 1.1.2.5 向业务层请求查看客户信息

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|切换到个人中心界面|客户id|浏览器向服务器发起 /api/user/{id}/getUserInfo GET请求|通过|

##### 1.1.2.6 收集并向业务层请求修改客户信息

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|在客户信息界面点击“修改信息”按钮|收集的修改信息|浏览器向服务器发起 /api/user/userInfo PUT请求|通过|

测试数据：
       - 用户名：我是用户名

##### 1.1.2.7 收集并向业务层请求修改客户密码

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|在客户信息界面点击“修改密码”按钮|收集的表单信息|浏览器向服务器发起 /api/user/userInfo PUT请求|通过|

测试数据：
       - 原密码：123456
       - 新密码：666666
       - 请确认：666666

##### 1.1.2.8 向业务层请求升级VIP

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|在客户信息界面点击“成为VIP”按钮|客户id，开通天数，VIP类型|浏览器向服务器发起 /api/user/vip POST请求|通过|

##### 1.1.2.9 收集并向业务层请求修改用户头像

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|在客户信息界面点击用户头像旁边的矩形框|客户id，头像信息|浏览器向服务器发起 /api/user/userInfo PUT请求|通过|

测试数据：
       - 图片

##### 1.1.2.10 向业务层请求显示入住人列表

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|切换到个人中心界面|客户id|浏览器向服务器发起 /api/person/by-user-id GET请求|通过|

##### 1.1.2.11 收集并向业务层请求修改入住人信息

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|修改入住人信息后点击“保存”按钮|收集的表单信息|浏览器向服务器发起 /api/person PUT请求|通过|

测试数据：
       - 用户手机号：111111006677

##### 1.1.2.12 向业务层请求删除入住人信息

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|在入住人列表中点击“删除信息”并确定|入住人id|浏览器向服务器发起 /api/person/rm POST请求|通过|

##### 1.1.2.13 收集并向业务层请求添加入住人信息

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|点击“添加用户”按钮填写表单后点击确定|收集的表单信息|浏览器向服务器发起 /api/person/new POST请求|通过|

测试数据：
       - 用户名：张三
       - 身份证号：11177744988
       - 手机号：15188999447
       - 生日：1980-03-03

##### 1.1.2.14 向业务层请求查看订单列表

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|切换到个人中心界面|客户id|浏览器向服务器发起 /api/order/getUserOrders GET请求|通过|

##### 1.1.2.15 向业务层请求撤销订单

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|选择撤销订单并确定|订单id|浏览器向服务器发起 /api/order/{orderId}/annulOrder GET请求|通过|

##### 1.1.2.16 收集并向业务层请求评论订单

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|订单显示“已入住”，填写评价后点击提交|收集的表单信息|浏览器向服务器发起 /api/comment POST请求|通过|

测试数据：
       - 五颗星
       - Very Good!

### 1.2 业务逻辑层单元测试

#### 1.2.1 功能测试

##### 1.2.1.1 向业务层提供从数据层获取的酒店列表

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|收到 /api/hotel/all 请求|无|向数据层请求酒店列表并返回给请求|通过|

##### 1.2.1.2 向业务层提供从数据层获取的酒店详情信息

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|收到 /api/hotl/{hotelUd}/detail 请求|hotelId|向数据层请求酒店详情信息并返回给请求|通过|

##### 1.2.1.3 向数据层请求存储从业务层获取的预定订单信息
|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|收到 /api/person/by-user-id 请求|userId|向数据层请求当前客户入住人列表并返回给请求|通过|
|收到 /api/order/addOrder 请求|orderVO|向数据层请求当存储预定订单信息|通过|
|收到 /api/coupon/orderMatchCoupons 请求|order|向数据层请求匹配的优惠券信息并返回给请求|通过|

##### 1.2.1.4 向业务层提供从数据层获取的酒店评论
|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|收到 /api/comment/by-hotel 请求|hotelId|向数据层请求酒店评论并返回给请求|通过|

##### 1.2.1.5 向业务层提供从数据层获取的客户信息
|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|收到 /api/user/{id}/getUserInfo 请求|id|向数据层请求客户信息并返回给请求|通过|

##### 1.2.1.6 向数据层请求更新从业务层获取的客户信息
|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|收到 /api/user/userInfo 请求|userInfo|向数据层请求更新客户信息|通过|

##### 1.2.1.7 向数据层请求更新从业务层获取的客户密码
|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|收到 /api/user/userInfo 请求|userInfo|向数据层请求更新客户密码|通过|

##### 1.2.1.8 向数据层请求存储从业务层获取的VIP信息
|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|收到 /api/user/vip 请求|vipForm|向数据层请求存储客户VIP信息|通过|

##### 1.2.1.9 向数据层请求更新从业务层获取的头像信息
|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|收到 /api/user/userInfo 请求|userInfo|向数据层请求更新客户头像信息|通过|

##### 1.2.1.10 向业务层提供从数据层获取的入住人信息
|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|收到 /api/person/by-user-id 请求|userId|向数据层请求客户账户下所有入住人信息并返回给请求|通过|

##### 1.2.1.11 向数据层请求更新从业务层获取的入住人信息
|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|收到 /api/person 请求|personVO|向数据层请求更新入住人信息|通过|

##### 1.2.1.12 向数据层请求删除从业务层获取的入住人信息
|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|收到 /api/person/rm 请求|id|向数据层请求删除入住人信息|通过|

##### 1.2.1.13 向数据层请求添加从业务层获取的入住人信息
|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|收到 /api/person/new 请求|personVO|向数据层请求添加入住人信息|通过|

##### 1.2.1.14 向业务层提供从数据层获取的订单列表
|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|收到 /api/order/{userId}/getUserOrders 请求|userId|向数据层请求订单列表并返回给请求|通过|

##### 1.2.1.15 向数据层请求撤销从业务层获取的订单信息
|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|收到 /api/order/{orderId}/annulOrder 请求|orderId|向数据层请求撤销订单信息|通过|

##### 1.2.1.16 向数据层请求存储从业务层获取的评论信息
|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|收到 /api/comment 请求|commentVO|向数据层请求存储评论信息|通过|

### 1.3 数据层单元测试

#### 1.3.1 功能测试

##### 1.3.1.1 向业务层提供酒店列表

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|调用 retrieveHotels 方法|无|返回数据库中所有酒店信息|通过|

##### 1.3.1.2 向业务层提供酒店详情信息

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|调用 selectById 方法|hotelId|返回数据库中当前酒店的信息|通过|

##### 1.3.1.3 存储从业务层获取的预定订单信息

| 触发条件              | 参数 | 预期结果                         | 测试结果 |
| --------------------- | ---- | -------------------------------- | -------- |
| 调用 selectByUserId 方法 | userId   | 返回数据库中当前客户的入住人信息 | 通过     |
| 调用 addOrder 方法 | order   | 存储订单信息至 orderlist 表 | 通过     |
| 调用 getMatchOrderCoupon 方法 | order   | 返回数据库中匹配的优惠券信息 | 通过     |

##### 1.3.1.4 向业务层提供酒店评论

| 触发条件          | 参数 | 预期结果               | 测试结果 |
| ----------------- | ---- | ---------------------- | -------- |
| 调用 getComment 方法 | hotelId | 返回数据库中当前酒店的评论信息 | 通过     |

##### 1.3.1.5 向业务层提供客户信息

| 触发条件                   | 参数 | 预期结果                 | 测试结果 |
| -------------------------- | ---- | ------------------------ | -------- |
| 调用 getUserInfo 方法 | id   | 返回数据库中当前客户信息 | 通过     |

##### 1.3.1.6 更新从业务层获取的客户信息

| 触发条件             | 参数 | 预期结果              | 测试结果 |
| -------------------- | ---- | --------------------- | -------- |
| 调用 updateUserInfo 方法 | userInfo   | 更新客户信息至 user 表 | 通过     |

##### 1.3.1.7 更新从业务层获取的客户密码

| 触发条件                | 参数 | 预期结果     | 测试结果 |
| ----------------------- | ---- | ------------ | -------- |
| 调用 updateUserInfo 方法 | userInfo   | 更新客户密码至 user 表 | 通过     |

##### 1.3.1.8 存储从业务层获取的VIP信息

| 触发条件                | 参数     | 预期结果             | 测试结果 |
| ----------------------- | -------- | -------------------- | -------- |
| 调用 vip 方法 | vipForm | 更新客户VIP信息至 user 表 | 通过     |

##### 1.3.1.9 更新从业务层获取的头像信息

| 触发条件             | 参数 | 预期结果              | 测试结果 |
| -------------------- | ---- | --------------------- | -------- |
| 调用 updateUserInfo 方法 | userInfo   | 更新客户头像信息至 user 表 | 通过     |

##### 1.3.1.10 向业务层提供入住人信息

| 触发条件                | 参数 | 预期结果     | 测试结果 |
| ----------------------- | ---- | ------------ | -------- |
| 调用 selectByUserId 方法 | userId   | 返回数据库中当前客户的入住人信息 | 通过     |

##### 1.3.1.11 更新从业务层获取的入住人信息

| 触发条件                | 参数     | 预期结果             | 测试结果 |
| ----------------------- | -------- | -------------------- | -------- |
| 调用 select 方法 | id | 更新入住人信息至 person 表 | 通过     |

##### 1.3.1.12 删除从业务层获取的入住人信息

| 触发条件             | 参数 | 预期结果              | 测试结果 |
| -------------------- | ---- | --------------------- | -------- |
| 调用 delete 方法 | id   | 删除 person 表中的入住人信息 | 通过     |

##### 1.3.1.13 添加从业务层获取的入住人信息

| 触发条件                | 参数 | 预期结果     | 测试结果 |
| ----------------------- | ---- | ------------ | -------- |
| 调用 insert 方法 | person   | 添加入住人信息至 person 表 | 通过     |

##### 1.3.1.14 向业务层提供订单列表

| 触发条件                | 参数     | 预期结果             | 测试结果 |
| ----------------------- | -------- | -------------------- | -------- |
| 调用 getUserOrders 方法 | userId | 返回数据库中当前客户的订单信息 | 通过     |

##### 1.3.1.15 撤销从业务层获取的订单信息

| 触发条件                | 参数 | 预期结果     | 测试结果 |
| ----------------------- | ---- | ------------ | -------- |
| 调用 annulOrder 方法 | orderId   | 更改订单状态至 orderlist 表 | 通过     |

##### 1.3.1.16 存储从业务层获取的评论信息

| 触发条件                | 参数     | 预期结果             | 测试结果 |
| ----------------------- | -------- | -------------------- | -------- |
| 调用 comment 方法 | commentVO | 存储评论信息至 comment 表 | 通过     |

## 2. 集成测试

### 2.1 功能测试

#### 2.1.1 向业务层请求查看酒店列表

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|访问首页界面|无|显示酒店列表|通过|

#### 2.1.2 向业务层请求查看酒店详情信息

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|点击酒店列表中的酒店|hotelId|显示酒店详情信息|通过|

#### 2.1.3 收集并向业务层请求添加酒店预定订单信息

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|点击“预定”按钮|客户id|显示入住人信息|通过|
|点击“下单”按钮|订单信息|列表中显示新增的订单信息|通过|
|预定信息满足优惠券要求|订单信息|订单结算总价按优惠券规则减少|通过|

#### 2.1.4 向业务层请求查看用户评论

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|点击酒店详情页面的“用户评论”选项|酒店id|显示酒店的评论信息|通过|

#### 2.1.5 向业务层请求查看客户信息

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|切换到个人中心界面|客户id|显示客户信息|通过|

#### 2.1.6 收集并向业务层请求修改客户信息

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|在客户信息界面点击“修改信息”按钮|客户信息|显示修改之后的客户信息|通过|

#### 2.1.7 收集并向业务层请求修改客户密码

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|在客户信息界面点击“修改密码”按钮|客户密码信息|显示密码修改成功|通过|

#### 2.1.8 向业务层请求升级VIP

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|在客户信息界面点击“成为VIP”按钮|VIP信息|显示VIP升级成功|通过|

#### 2.1.9 收集并向业务层请求修改用户头像

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|在客户信息界面点击用户头像旁边的矩形框|图片|显示修改后的用户头像|通过|

#### 2.1.10 向业务层请求显示入住人列表

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|切换到个人中心界面|客户id|显示客户账户下的入住人信息|通过|

#### 2.1.11 收集并向业务层请求修改入住人信息

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|修改入住人信息后点击“保存”按钮|入住人信息|显示修改后的入住人信息|通过|

#### 2.1.12 向业务层请求删除入住人信息

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|在入住人列表中点击“删除信息”并确定|入住人id|显示删除成功|通过|

#### 2.1.13 收集并向业务层请求添加入住人信息

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|点击“添加用户”按钮填写表单后点击确定|入住人信息|显示新增的入住人信息|通过|

#### 2.1.14 向业务层请求查看订单列表

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|切换到个人中心界面|客户id|显示订单信息|通过|

#### 2.1.15 向业务层请求撤销订单

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|选择撤销订单并确定|订单id|显示撤销成功|通过|

#### 2.1.16 收集并向业务层请求评论订单

|触发条件|参数|预期结果|测试结果|
|-|-|-|-|
|订单显示“已入住”，填写评价后点击提交|收集的表单信息|显示评论成功|通过|

## 3. 文档修改历史

| 修改人员 | 日期       | 修改原因                                             | 版本号 |
| :------: | ---------- | ---------------------------------------------------- | ------ |
|  杜铭哲  | 2020.6.30 | 完成用户测试文档              | v1.0 |