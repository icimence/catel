<template>
    <a-modal
            :visible="addPlatformCouponModalVisible"
            @cancel="cancel"
            @ok="handleSubmit"
            cancelText="取消"
            okText="确定"
            title="添加全平台优惠策略"
    >
        <!-- 这里是添加策略模态框区域，请编写表单 -->
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="优惠券类型" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                    'type',
                    { rules: [{ required: true, message: '请选择优惠券类型' }] }]"
                >
                    <a-select-option @click="changeType('Birthday')" value="Birthday">生日优惠</a-select-option>
                    <a-select-option @click="changeType('MultiRoom')" value="MultiRoom">多间优惠</a-select-option>
                    <a-select-option @click="changeType('Target')" value="Target">满减优惠</a-select-option>
                    <a-select-option @click="changeType('Time')" value="Time">限时优惠</a-select-option>
                    <a-select-option @click="changeType('Vip')" value="Vip">VIP专享优惠</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="券名" v-bind="formItemLayout">
                <a-input
                        autocomplete="off"
                        placeholder="请填写券名"
                        v-decorator="['name', { rules: [{ required: true, message: '请填写券名' }] }]"
                />
            </a-form-item>
            <a-form-item label="优惠简介" v-bind="formItemLayout">
                <a-textarea
                        :rows="4"
                        placeholder="请填写优惠简介"
                        v-decorator="['description', { rules: [{ required: true, message: '请填写优惠简介' }] }]"
                />
            </a-form-item>
            <a-form-item label="达标金额" v-bind="formItemLayout" v-if="this.type === 'Target'">
                <a-input
                        autocomplete="off"
                        placeholder="请填写达标金额"
                        v-decorator="['targetMoney', { rules: [{ required: true, message: '请填写达标金额' }] }]"
                />
            </a-form-item>
            <a-form-item label="达标房间数" v-bind="formItemLayout" v-if="this.type === 'MultiRoom'">
                <a-input
                        autocomplete="off"
                        placeholder="请填写达标房间数"
                        v-decorator="['roomNumber', { rules: [{ required: true, message: '请填写达标房间数' }] }]"
                />
            </a-form-item>
            <a-form-item label="优惠起止时间" v-bind="formItemLayout" v-if="this.type === 'Time'">
                <div>
                    <a-date-picker
                            :disabled-date="disabledStartDate"
                            @openChange="handleStartOpenChange"
                            format="YYYY-MM-DD HH:mm:ss"
                            placeholder="Start"
                            show-time
                            v-decorator="['startValue',
                {
                rules: [{ required: true, message: '请选择优惠开始时间' }]
                }
                ]"
                    />
                    <a-date-picker
                            :disabled-date="disabledEndDate"
                            :open="endOpen"
                            @openChange="handleEndOpenChange"
                            format="YYYY-MM-DD HH:mm:ss"
                            placeholder="End"
                            show-time
                            v-decorator="['endValue',
                {
                rules: [{ required: true, message: '请选择优惠结束时间' }]
                }
                ]"
                    />
                </div>
            </a-form-item>
            <a-form-item label="VIP等级要求" v-bind="formItemLayout" v-if="this.type === 'Vip'">
                <a-select
                        v-decorator="[
                    'vipType',
                    { rules: [{ required: true, message: '请选择VIP等级要求' }] }]"
                >
                    <a-select-option value="0">所有VIP等级</a-select-option>
                    <a-select-option value="1">年度会员专享</a-select-option>
                    <a-select-option value="2">年度大会员专享</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="优惠金额" v-bind="formItemLayout">
                <a-input
                        autocomplete="off"
                        placeholder="请填写优惠金额"
                        v-decorator="['discountMoney', { rules: [{ required: true, message: '请填写优惠金额' }] }]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';
    const moment = require('moment');
    export default {
        name: "addAllPlatformCoupon",
        data() {
            return {
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
                type: '',
                startValue: null,
                endValue: null,
                endOpen: false,
            };
        },
        computed: {
            ...mapGetters([
                'addPlatformCouponModalVisible',
            ]),
        },
        beforeCreate() {
            // 表单名默认为“form”
            this.form = this.$form.createForm(this, {name: 'addAllPlatformCoupon'});
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                "set_addPlatformCouponModalVisible"
            ]),
            ...mapActions([
                'addPlatformCoupon',
            ]),
            disabledStartDate(startValue) {
                const endValue = this.form.getFieldValue('endValue');
                if (!startValue || !endValue) {
                    return false;
                }
                return startValue.valueOf() > endValue.valueOf();
            },
            disabledEndDate(endValue) {
                const startValue = this.form.getFieldValue('startValue');
                if (!endValue || !startValue) {
                    return false;
                }
                return startValue.valueOf() >= endValue.valueOf();
            },
            handleStartOpenChange(open) {
                if (!open) {
                    this.endOpen = true;
                }
            },
            handleEndOpenChange(open) {
                this.endOpen = open;
            },
            cancel() {
                this.set_addPlatformCouponModalVisible(false);
            },
            changeType(v) {
                this.type = v;
                console.log(this.type);
            },
            handleSubmit(e) {
                const that = this;
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    let data;
                    if (!err) {
                        data = {};
                        if (that.type === 'Time') {
                            data = {
                                type: this.form.getFieldValue('type'),
                                name: (this.form.getFieldValue('name')),
                                description: (this.form.getFieldValue('description')),
                                discountMoney: Number(this.form.getFieldValue('discountMoney')),
                                hotelId: -1,
                                startTime: moment(this.form.getFieldValue('startValue')).format('YYYY-MM-DD HH:mm:ss'),
                                endTime: moment(this.form.getFieldValue('endValue')).format('YYYY-MM-DD HH:mm:ss'),
                                status: 'Available',
                            };
                        } else if (that.type === 'Target') {
                            data = {
                                type: this.form.getFieldValue('type'),
                                name: (this.form.getFieldValue('name')),
                                description: (this.form.getFieldValue('description')),
                                targetMoney: Number(this.form.getFieldValue('targetMoney')),
                                discountMoney: Number(this.form.getFieldValue('discountMoney')),
                                hotelId: -1,
                                status: 'Available',
                            };
                        } else if (that.type === 'MultiRoom') {
                            data = {
                                type: this.form.getFieldValue('type'),
                                name: (this.form.getFieldValue('name')),
                                description: (this.form.getFieldValue('description')),
                                multiRoomTarget: Number(this.form.getFieldValue('roomNumber')),
                                discountMoney: Number(this.form.getFieldValue('discountMoney')),
                                hotelId: -1,
                                status: 'Available',
                            };
                        } else if (that.type === 'Vip') {
                            data = {
                                type: this.form.getFieldValue('type'),
                                name: (this.form.getFieldValue('name')),
                                description: (this.form.getFieldValue('description')),
                                level: this.form.getFieldValue('vipType'),
                                discountMoney: Number(this.form.getFieldValue('discountMoney')),
                                hotelId: -1,
                                status: 'Available',
                            };
                        } else {
                            data = {
                                // 这里添加接口参数
                                type: this.form.getFieldValue('type'),
                                name: (this.form.getFieldValue('name')),
                                description: (this.form.getFieldValue('description')),
                                discountMoney: Number(this.form.getFieldValue('discountMoney')),
                                hotelId: -1,
                                status: 'Available',
                            };
                        }
                        console.log(data);
                        this.addPlatformCoupon(data);
                        this.form.resetFields();
                    }
                });
            },
        },
    }
</script>

<style scoped>

</style>
