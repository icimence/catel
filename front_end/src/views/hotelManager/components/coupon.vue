<template>
    <div>
        <a-modal
                :footer="null"
                :visible="couponVisible"
                @cancel="cancel"
                title="优惠策略"
                width="900px"
        >
            <!-- 这里是模态框内容区域，请编写列表代码与添加策略按钮 -->
            <div class="button">
                <a-button @click="addCoupon" type="primary">
                    <a-icon type="plus"/>
                    添加优惠策略
                </a-button>
            </div>
            <a-table :columns="columns" :dataSource="couponList" bordered>
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
                            title="确定想删除该优惠吗？"
                    >
                        <a-button type="danger">删除优惠<a-icon type="delete" /></a-button>
                    </a-popconfirm>
                </span>
            </a-table>

        </a-modal>
        <AddCoupon></AddCoupon>
    </div>
</template>
<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';
    import AddCoupon from './addCoupon';

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
        name: 'coupon',
        data() {
            return {
                columns,
            };
        },
        components: {
            AddCoupon,
        },
        computed: {
            ...mapGetters([
                'couponVisible',
                'couponList',
            ]),
        },
        methods: {
            ...mapMutations([
                'set_addCouponVisible',
                'set_couponVisible',
            ]),
            ...mapActions([
                'getHotelCoupon',
                "deleteHotelCoupon"
            ]),
            cancel() {
                this.set_couponVisible(false);
            },
            addCoupon() {
                this.set_addCouponVisible(true);
                this.set_couponVisible(false);
            },
            deleteCoupon(record) {
                this.deleteHotelCoupon(record.id);
            },
            cancelDelete() {

            },
        },
    };
</script>
<style scoped>
    .button {
        width: 100%;
        text-align: right;
        margin-top: 20px;
        margin-bottom: 20px;
    }
</style>
