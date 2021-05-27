<template>
    <a-modal
            :visible="orderModalVisible"
            @cancel="cancelOrder"
            @ok="handleSubmit"
            cancelText="取消"
            okText="下单"
            title="预定详情"
    >
        <a-form :form="form">
            <h2>房间信息</h2>
            <a-form-item label="房型信息" v-bind="formItemLayout">
                <span v-if="currentOrderRoom.roomType === 'BigBed'">大床房</span>
                <span v-if="currentOrderRoom.roomType === 'DoubleBed'">双床房</span>
                <span v-if="currentOrderRoom.roomType === 'Family'">家庭房</span>
            </a-form-item>
            <a-form-item label="早餐信息" v-bind="formItemLayout">
                <span v-if="currentOrderRoom.breakfast === true">提供早餐服务</span>
                <span v-if="currentOrderRoom.breakfast === false">不提供早餐服务</span>
            </a-form-item>
            <a-form-item label="入住人身份" v-bind="formItemLayout">
                <a-select @change="handlePersonChange" default-value="请选择用户" style="width: 128px" v-decorator="[
                        'person',
                        {
                            rules: [{ required: true, message: '请选择入住人身份' }]
                        }
                    ]">
                    <a-select-option :key="item.index" :value="item.id" v-for="item in personList">
                        {{item.realName}}
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="入住日期" v-bind="formItemLayout">
                <a-range-picker
                        :placeholder="['入住日期','退房日期']"
                        :disabled-date="disabledDate"
                        @change="changeDate"
                        format="YYYY-MM-DD"
                        v-decorator="[
                        'date',
                        {
                            rules: [{ required: true, message: '请选择入住时间' }]
                        }
                    ]"
                />
            </a-form-item>
            <a-form-item label="剩余房间数" v-bind="formItemLayout">
                <span>{{minRoom}}</span>
            </a-form-item>
            <a-form-item label="入住人数" v-bind="formItemLayout">
                <a-select
                        @change="changePeopleNum"
                        @click.native="selectPeopleNum()"
                        placeholder="请选择入住人数"
                        v-decorator="[
                        'peopleNum',
                        { rules: [{ required: true, message: '请选择入住人数' }] },
                    ]"
                >
                    <a-select-option :key='item' :value="item" v-for="item in peopleNumList">
                        {{item}}
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="有无儿童" v-bind="formItemLayout">
                <a-radio-group
                        v-decorator="[
                        'haveChild',
                        { rules: [{required: true, message: '请选择有无儿童入住', }] }
                    ]"
                >
                    <a-radio :value="1">有</a-radio>
                    <a-radio :value="0">无</a-radio>
                </a-radio-group>
            </a-form-item>
            <a-form-item label="房间数" v-bind="formItemLayout">
                <a-select
                        @change="changeRoomNum"
                        placeholder="请选择房间数"
                        v-decorator="[
                        'roomNum',
                        { rules: [{ required: true, message: '请选择房间数' }] },
                    ]"
                >
                    <a-select-option :value="1">
                        1
                    </a-select-option>
                    <a-select-option :value="2">
                        2
                    </a-select-option>
                    <a-select-option :value="3">
                        3
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="房间单价" v-bind="formItemLayout">
                <span>{{ currentOrderRoom.price }}</span>
            </a-form-item>
            <a-form-item label="总价" v-bind="formItemLayout">
                <span>￥{{ totalPrice }}</span>
            </a-form-item>
            <a-divider></a-divider>
            <h2 v-if="orderMatchCouponList.length>0">优惠</h2>
            <div class="coupon">
                <a-checkbox-group @change="onchange" v-model="checkedList">
                    <a-table
                            :columns="columns"
                            :dataSource="orderMatchCouponList"
                            bordered
                            v-if="orderMatchCouponList.length>0"
                    >
                        <a-checkbox
                                :value="record"
                                slot="id"
                                slot-scope="record"
                        >
                        </a-checkbox>
                    </a-table>
                </a-checkbox-group>
            </div>

            <a-divider></a-divider>
            <h2>结算</h2>
            <a-form-item label="会员折扣" v-bind="formItemLayout" v-if="this.userInfo.vipType !== 'Nil'">
            <span v-if="userInfo.vipType === 'Small'">您是年度会员，享受95折优惠</span>
            <span v-if="userInfo.vipType === 'Big'">您是年度大会员，享受92折优惠</span>
        </a-form-item>
            <a-form-item label="结算后总价" v-bind="formItemLayout">
                <span>￥{{ finalPrice }}</span>
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';

    const moment = require('moment');
    const columns = [
        {
            title: '勾选',
            dataIndex: 'id',
            scopedSlots: {customRender: 'id'},
        },
        {
            title: '优惠类型',
            dataIndex: 'name',
            scopedSlots: {customRender: 'name'},
        },
        {
            title: '优惠简介',
            dataIndex: 'description',

        },
        {
            title: '优惠金额',
            dataIndex: 'discountMoney',
        },
    ];
    export default {
        name: 'orderModal',
        data() {
            return {
                minRoom: '请先选择入住日期',
                peopleNumList: [],
                formItemLayout: {
                    labelCol: {
                        xs: {span: 12},
                        sm: {span: 6},
                    },
                    wrapperCol: {
                        xs: {span: 24},
                        sm: {span: 16},
                    },
                },
                totalPrice: '',
                columns,
                chooseValue: 1,
                checkedList: [],
                finalPrice: '',
                haveBreakfast: false,
                // endOpen: false,
                // startValue: null,
                // endValue: null,
                noteDes: '感谢您使用本站使用酒店预定服务，您刚刚的预定已处理，请刷新以查看最新的剩余房间数量。',
            };
        },
        computed: {
            ...mapGetters([
                'availableRoomList',
                'orderModalVisible',
                'currentOrderRoom',
                'currentHotelId',
                'currentHotelInfo',
                'userId',
                'orderMatchCouponList',
                'personList',
                'userInfo',
                "orderSuccess"
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'orderModal'});
        },
        created() {
            for (let i = 1; i < 5; i++) {
                this.peopleNumList.push(i);
            }
            console.log('显示当前房间信息');
            console.log(this.currentOrderRoom);
            // 模拟值
            //this.peopleNumList = [1, 2, 3, 4]
        },
        methods: {
            ...mapMutations([
                'set_orderModalVisible',
                'set_orderMatchCouponList',
                "set_orderSuccess"
            ]),
            ...mapActions([
                'addOrder',
                'getOrderMatchCoupons',
                'getAvailableRoom',
            ]),
            cancelOrder() {
                this.set_orderModalVisible(false);
                this.form.resetFields();
            },
            confirmOrder() {

            },
            changeDate(v) {
                this.getAvailableRoom(this.currentOrderRoom.id);
                // var startIndex = moment(v[0]).diff(moment(),'day')
                let startIndex = 0;
                let endIndex = 0;
                if (moment().toDate().getMonth() === moment(v[0]).toDate().getMonth()) {
                    startIndex = moment(v[0]).toDate().getDate() - moment().toDate().getDate();
                } else {
                    startIndex = moment().daysInMonth() - moment().toDate().getDate() + moment(v[0]).toDate().getDate();
                }
                if (moment().toDate().getMonth() === moment(v[1]).toDate().getMonth()) {
                    endIndex = moment(v[1]).toDate().getDate() - moment().toDate().getDate();
                } else {
                    endIndex = moment().daysInMonth() - moment().toDate().getDate() + moment(v[1]).toDate().getDate();
                }
                // var endIndex = moment(v[1]).diff(moment(),'day')
                let min = this.availableRoomList[startIndex];
                for (let i = startIndex; i < endIndex; i++) {
                    if (this.availableRoomList[i] < min) {
                        min = this.availableRoomList[i];
                    }
                }
                this.minRoom = min;
                if (this.totalPrice !== '') {
                    this.totalPrice = this.form.getFieldValue('roomNum') * moment(v[1]).diff(moment(v[0]), 'day') * Number(this.currentOrderRoom.price);
                    this.calculateFinalPrice();
                }
            },
            changePeopleNum(v) {

            },
            selectPeopleNum() {

            },
            changeRoomNum(v) {
                this.totalPrice = Number(v) * Number(this.currentOrderRoom.price) * moment(this.form.getFieldValue('date')[1]).diff(moment(this.form.getFieldValue('date')[0]), 'day');
                this.calculateFinalPrice();
            },
            disabledDate(current) {
                // Can not select days before today and today
                // (current && current < moment().subtract(1, "days")) ||
                return (current && current < moment().subtract(1, 'days')) || current > moment().add(28, 'days').toDate();
            },
            calculateFinalPrice() {
                this.finalPrice = this.totalPrice;
                if (this.userInfo.vipType === 'Small') {
                    this.finalPrice = Math.ceil(this.finalPrice * 0.95);
                    this.noteDes = '尊敬的年度会员： \n\t' + this.noteDes;
                } else if (this.userInfo.vipType === 'Big') {
                    this.finalPrice = Math.ceil(this.finalPrice * 0.92);
                    this.noteDes = '尊敬的年度大会员： \n\t' + this.noteDes;
                } else {
                    this.noteDes = '尊敬的用户： \n\t' + this.noteDes;
                }
            },
            onchange() {
                this.finalPrice = this.totalPrice;
                if (this.checkedList.length > 0) {
                    this.orderMatchCouponList.filter(item => this.checkedList.indexOf(item.id) != -1).forEach(item => this.finalPrice = this.finalPrice - item.discountMoney);
                } else {

                }
            },
            handlePersonChange(v) {
                this.chooseValue = Number(v);
            },
            handleSubmit(e) {
                this.haveBreakfast = this.currentOrderRoom.breakfast;
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        if (this.minRoom < this.form.getFieldValue('roomNum')) {
                            this.$message.error('剩余房间数不足');
                            return;
                        }
                        const data = {
                            breakfast: this.haveBreakfast,
                            personId: this.chooseValue,
                            hotelId: this.currentHotelId,
                            hotelName: this.currentHotelInfo.name,
                            userId: Number(this.userId),
                            checkInDate: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD'),
                            checkOutDate: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD'),
                            roomType: this.currentOrderRoom.roomType === 'BigBed' ? 'BigBed' : this.currentOrderRoom.roomType === 'DoubleBed' ? 'DoubleBed' : 'Family',
                            roomNum: this.form.getFieldValue('roomNum'),
                            haveChild: this.form.getFieldValue('haveChild'),
                            createDate: '',
                            peopleNum: Number(this.form.getFieldValue('peopleNum')),
                            price: Number(this.checkedList.length > 0 ? this.finalPrice : this.totalPrice),
                        };
                        console.log('显示提交的数据');
                        console.log(data);
                        this.addOrder(data);
                        this.form.resetFields();
                        const dataCoupon = {
                            orderMatchCouponList: [],
                        };
                        this.set_orderMatchCouponList(dataCoupon);
                        this.totalPrice = '';
                        this.finalPrice = '';
                        this.minRoom = '请先选择入住日期';
                        console.log(this.orderSuccess);
                        if (this.orderSuccess) {
                            this.set_orderSuccess(false);
                        }
                    }
                });
            },
        },
        watch: {
            totalPrice(val) {
                let data = {
                    userId: this.userId,
                    personId: this.chooseValue,
                    hotelId: this.currentHotelId,
                    price: this.totalPrice,
                    roomNum: this.form.getFieldValue('roomNum'),
                    checkInDate: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD'),
                    checkOutDate: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD'),
                };
                console.log('显示订单信息：');
                console.log(data);
                this.getOrderMatchCoupons(data);
            },
        },
        // getCurrentTime(){
        //     var date = new Date();
        //     var year = date.getFullYear();
        //     var currentMonth = date.getMonth();
        //     var month = date.getMonth() + 1;
        //     var day = date.getDate();
        //     month = month < 10 ? ("0" + month) : month;
        //     day = day < 10 ? ("0" + day) : day;
        //     var Timer1 = year+'-'+ currentMonth+'-'+day;
        //     var Timer2 = year+'-'+month+'-'+day;
        // }

    };
</script>
<style>
    .coupon{
        align-items: center;
        text-align: center;
    }
</style>
