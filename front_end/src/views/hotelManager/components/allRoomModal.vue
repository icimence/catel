<template>
    <div>
        <a-modal
                :footer="null"
                :visible="allRoomModalVisible"
                @cancel="cancel"
                title="房型"
                width="900px"
        >
            <!-- 这里是模态框内容区域，请编写列表代码与添加策略按钮 -->
            <div class="button">
                <a-button @click="addRoom" type="primary">
                    <a-icon type="plus"/>
                    添加房型
                </a-button>
            </div>
            <a-table :columns="columns" :dataSource="roomList" bordered>
                <span slot="roomType" slot-scope="text">
                    <span v-if="text === 'BigBed'">大床房</span>
                    <span v-if="text === 'DoubleBed'">双床房</span>
                    <span v-if="text === 'Family'">家庭房</span>
                </span>
                <span slot="breakfast" slot-scope="text">
                    <span v-if="text === true">有</span>
                    <span v-if="text === false">无</span>
                </span>
                <span slot="price" slot-scope="text">
                    <span>￥{{ text }}</span>
                </span>
                <span slot="action" slot-scope="text, record">
                    <a-popconfirm
                            @cancel="cancelDelete"
                            @confirm="deleteThisRoom(record)"
                            cancelText="取消"
                            okText="确定"
                            title="确定想删除该房型吗？"
                    >
                       <a-button size="small" type="danger">删除房型<a-icon type="delete" /></a-button>
                    </a-popconfirm>
                </span>
            </a-table>

        </a-modal>
        <AddRoomModal></AddRoomModal>
    </div>
</template>

<script>
    import {mapGetters,mapActions,mapMutations} from 'vuex';
    import AddRoomModal from "./addRoomModal";
    const columns = [
        {
            title: '房型',
            dataIndex: 'roomType',
            key: 'roomType',
            scopedSlots: {customRender: 'roomType'},
            width: '22%'
        },
        {
            title: '早餐',
            dataIndex: 'breakfast',
            key: 'breakfast',
            scopedSlots: {customRender: 'breakfast'},
            width: '22%'
        },
        {
            title: '房间数量',
            key: 'total',
            dataIndex: 'total',
            scopedSlots: {customRender: 'total'},
            width: '22%'
        },
        {
            title: '房价',
            key: 'price',
            dataIndex: 'price',
            scopedSlots: {customRender: 'price'},
            width: '22%'
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];

    export default {
        name: "allRoomModal",
        data(){
            return{
                columns,
            };
        },
        components:{
            AddRoomModal
        },
        computed:{
            ...mapGetters([
                "allRoomModalVisible",
                "roomList"
            ])
        },
        methods:{
            ...mapMutations([
                "set_addRoomModalVisible",
                "set_allRoomModalVisible"
            ]),
            ...mapActions([
                "deleteRoom"
            ]),
            cancel(){
                this.set_allRoomModalVisible(false);
            },
            addRoom(){
                this.set_allRoomModalVisible(false);
                this.set_addRoomModalVisible(true);
            },
            cancelDelete(){

            },
            deleteThisRoom(record){
                this.deleteRoom(record.id)
            }
        }
    }
</script>

<style scoped>
    .button {
        width: 100%;
        text-align: right;
        margin-top: 20px;
        margin-bottom: 20px;
    }
</style>
