<template>
    <a-modal
        :closable="false"
        :visible="announcementVisible"
        title="编辑促销文案"
    >
        <a-form :form="form">
            <a-form-item :label-col="{ span: 5 }" :wrapper-col="{ span: 12, offset: 2  }" label="促销文案">
                <a-textarea
                    :rows="10"
                    placeholder="请填写活动内容"
                    v-decorator="[ 'announcement' , { initialValue:this.currentHotelInfo.announcement }]"
                    v-if="modifyA"
                />
                <span v-else-if="this.currentHotelInfo.announcement === ''" style="opacity: 0.5">目前没有促销文案</span>
                <span v-else>{{this.currentHotelInfo.announcement}}</span>
            </a-form-item>
        </a-form>
        <template slot="footer">
            <a-button @click="saveModifyAnnouncement" type="primary" v-if="modifyA">保存</a-button>
            <a-button @click="modifyAnnouncement" type="primary" v-else>修改</a-button>
            <a-button @click="cancelSave" type="info" v-if="modifyA">取消</a-button>
            <a-button @click="cancelModifyAnnouncement" type="info" v-else>关闭</a-button>
        </template>

    </a-modal>

</template>

<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';

    export default {
        name: 'modifyAnnouncement',
        data() {
            return {
                form: this.$form.createForm(this, {name: 'modifyAnnouncement'}),
                modifyA: false,
            };
        },
        computed: {
            ...mapGetters([
                'currentHotelInfo',
                'announcementVisible',
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'modifyAnnouncement'});
        },
        mounted() {
        },
        methods: {
            ...mapMutations([
                'set_hotelInfoParams',
                'set_announcementVisible',
            ]),
            ...mapActions([
                'updateHotelInfo',
            ]),
            modifyAnnouncement() {
                this.form.setFieldsValue({
                    'announcement': this.currentHotelInfo.announcement,
                });
                this.modifyA = true;
            },
            cancelModifyAnnouncement() {
                this.modifyA = false;
                this.set_announcementVisible(false);
            },
            saveModifyAnnouncement() {
                console.log(this.form.getFieldValue('announcement'));
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            id: this.currentHotelInfo.id,
                            announcement: this.form.getFieldValue('announcement'),
                        };
                        this.set_hotelInfoParams(data);
                        this.updateHotelInfo();
                        this.modifyA = false;
                        this.form.resetFields();
                    }
                });
            },
            cancelSave() {
                this.modifyA = false;
            },
        },
    };
</script>

<style scoped>

</style>
