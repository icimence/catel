<template>
    <a-modal
        :visible="addHotelToManagerVisible"
        @cancel="cancel"
        @ok="cancel()"
        cancelText="取消"
        okText="关闭"
        title="指定酒店管理员"
        width="800px"
    >
        <form>
            <a-form-item>
                <h2>已绑定酒店列表</h2>
                <a-table
                    :columns="columns1"
                    :dataSource="hotelListMatchManager"
                    bordered
                >
                    <span slot="action" slot-scope="record">
                        <a-button @click="unbundle(record)" size="small" type="danger">取消绑定<a-icon type="unlock" /></a-button>
                    </span>
                </a-table>
            </a-form-item>
            <a-form-item>
                <h2>未绑定酒店列表</h2>
                <a-table
                    :columns="columns1"
                    :dataSource="unboundHotelList"
                    bordered
                >
                    <span slot="action" slot-scope="record">
                        <a-button @click="bundle(record)" size="small" type="primary">绑定<a-icon type="lock" /></a-button>
                    </span>
                </a-table>
            </a-form-item>
        </form>
    </a-modal>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';

    const columns1 = [
        {
            title: '酒店名',
            dataIndex: 'name',
        },
        {
            title: '地址',
            dataIndex: 'address',
        },
        // {
        //     title: '状态',
        //     dataIndex: 'managerId'
        // },
        {
            title: '酒店星级',
            dataIndex: 'hotelStar',
        },
        {
            title: '评分',
            dataIndex: 'rate',
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    export default {
        name: 'addHotelToManager',

        data() {
            return {
                columns1,
                form: this.$form.createForm(this, {name: 'addHotelToManager'}),
                managerUser: 0,
            };
        },
        computed: {
            ...mapGetters([
                'unboundHotelList',
                'hotelManagerId',
                'userId',
                'hotelListMatchManager',
                'addHotelToManagerVisible',
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'addHotelToManager'});
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addHotelToManagerVisible',
                'set_bindHotelAndManagerParams',
            ]),
            ...mapActions([
                'getHotelListByManager',
                'getUnboundHotelList',
                'bindHotelAndManager',
            ]),
            cancel() {
                this.set_addHotelToManagerVisible(false);
            },
            handleSubmit(e) {
                e.preventDefault();

            },
            bundle(record) {
                const data = {
                    hotelId: record.id,
                    managerId: this.hotelManagerId,
                };
                this.set_bindHotelAndManagerParams(data);
                this.bindHotelAndManager();
            },
            unbundle(record) {
                const data = {
                    hotelId: record.id,
                    managerId: -1,
                };
                this.set_bindHotelAndManagerParams(data);
                this.bindHotelAndManager();
            },
        },
    };
</script>
