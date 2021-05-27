<template>
    <div class="wrapper">
        <a-tabs
                :active-key="customActiveKey"
                @change="handleTabClick"
        >
            <a-tab-pane key="tab1" tab="平台信息管理">
                <div class="button">
                    <a-button @click="addAllPlatformCoupon" type="primary">
                        <a-icon type="plus"/>
                        添加优惠策略
                    </a-button>
                </div>
                <a-table :columns="columns" :dataSource="allPlatformCouponList" bordered>
                    <a-tag color="red" slot="type" slot-scope="type">
                    <span v-if="type ==='Birthday'">
                        生日满减优惠券
                    </span>
                        <span v-if="type ==='MultiRoom'">
                        多间优惠券
                    </span>
                        <span v-if="type ==='Time'">
                        限时优惠券
                    </span>
                        <span v-if="type ==='Target'">
                        满减优惠券
                    </span>
                        <span v-if="type ==='Vip'">
                        VIP专享优惠券
                    </span>
                    </a-tag>
                    <span slot="action" slot-scope="text,record">
            <a-popconfirm
                    @cancel="cancelDelete"
                    @confirm="deleteCoupon(record)"
                    cancelText="取消"
                    okText="确定"
                    title="确定想删除该平台优惠吗？"
            >
                <a-button type="danger">删除优惠<a-icon type="delete"/></a-button>
            </a-popconfirm>
            </span>
                </a-table>


            </a-tab-pane>
            <a-tab-pane key="tab2" tab="信用值充值">
                <div style="display: flex; flex-direction: column; align-items: center;">
                <div class="payForm" style="width: 100%;">
                    <a-form :form="form" style="margin-top: 80px; width: 100%;">
                        <a-form-item :label-col="{ span: 10 }" :wrapper-col="{ span: 5, offset: 1  }" label="充值用户名">
                            <a-input
                                    autocomplete="off"
                                    placeholder="请填写充值用户名"
                                    v-decorator="['userName', { rules: [{ required: true, message: '请输入充值用户名' }] }]"
                            />
                        </a-form-item>
                        <a-form-item :label-col="{ span: 10 }" :wrapper-col="{ span: 5, offset: 1  }" label="充值用户邮箱">
                            <a-input
                                    autocomplete="off"
                                    placeholder="请填写充值用户邮箱"
                                    v-decorator="['email', { rules: [{ required: true, type:'email',message: '请输入充值用户邮箱' }] }]"
                            />
                        </a-form-item>
                        <a-form-item :label-col="{ span: 10 }" :wrapper-col="{ span: 5, offset: 1  }" label="充值信用值额度">
                            <a-input
                                    autocomplete="off"
                                    placeholder="请填写充值信用值额度"
                                    v-decorator="['credit', { rules: [{ required: true, message: '请填写充值信用值额度' }] }]"
                            />
                        </a-form-item>
                    </a-form>
                </div>
                <div style="width: 90px; margin-top: 10px;">
                    <a-button @click="chargeCredit()" type="primary">
                        确认充值
                    </a-button>
                </div>
                </div>
            </a-tab-pane>
        </a-tabs>
        <AddAllPlatformCoupon></AddAllPlatformCoupon>
    </div>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';
    import AddAllPlatformCoupon from './components/addAllPlatformCoupon';
    import {getAllPlatformCouponAPI} from "../../api/coupon";

    const columns = [
        // 这里定义列表头
        {
            title: '优惠券名称',
            dataIndex: 'name',
            width: '30%',

        },
        {
            title: '优惠券类型',
            dataIndex: 'type',
            width: '20%',
            scopedSlots: {customRender: 'type'},
        },
        {
            title: '优惠简介',
            dataIndex: 'description',
            width: '25%',
        },
        {
            title: '优惠金额',
            dataIndex: 'discountMoney',
        },
        {
            title: '操作',
            dataIndex: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];

    export default {
        name: "manageMarket",
        data() {
            return {
                columns,
                customActiveKey: 'tab1',
                form: this.$form.createForm(this)
            }
        },
        components: {
            AddAllPlatformCoupon,
        },
        mounted() {
            this.getAllPlatformCouponList();
        },
        computed: {
            ...mapGetters([
                'allPlatformCouponList',
            ])
        },
        methods: {
            ...mapMutations([
                "set_addPlatformCouponModalVisible"
            ]),
            ...mapActions([
                "getAllPlatformCouponList",
                "deletePlatformCoupon",
                "creditUP"
            ]),
            addAllPlatformCoupon() {
                this.set_addPlatformCouponModalVisible(true);
            },
            deleteCoupon(record) {
                this.deletePlatformCoupon(record.id);
            },
            cancelDelete() {

            },
            handleTabClick(key){
                this.customActiveKey = key;
            },
            chargeCredit() {
                const validateFieldsKey = this.customActiveKey === "tab1" ? [] : ['userName', 'email', 'credit']
                this.form.validateFields(validateFieldsKey, {force: true}, async (err, values) => {
                    if (!err) {
                        const data = {
                            username: this.form.getFieldValue('userName'),
                            email: this.form.getFieldValue('email'),
                            creditDelta: this.form.getFieldValue('credit')
                        }
                        this.creditUP(data);
                        this.form.resetFields();
                    }
                });
            }
        }
    }
</script>

<style scoped>
    .wrapper {
        margin-top: 30px;
    }

    .button {
        width: 100%;
        text-align: right;
        margin-top: 30px;
        margin-bottom: 20px;
    }
</style>
