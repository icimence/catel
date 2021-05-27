<template>
    <a-modal
        :visible="addRoomModalVisible"
        @cancel="cancel"
        @ok="handleSubmit"
        cancelText="取消"
        okText="确定"
        title="录入客房"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="房型" v-bind="formItemLayout">
                <a-select
                    v-decorator="[
                    'roomType',
                    { rules: [{ required: true, message: '请选择房型' }] }]"
                >
                    <a-select-option value="BigBed">大床房</a-select-option>
                    <a-select-option value="DoubleBed">双床房</a-select-option>
                    <a-select-option value="Family">家庭房</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="早餐" v-bind="formItemLayout">
                <a-select
                    v-decorator="[
                    'breakfast',
                    { rules: [{ required: true, message: '请选择是否有早餐' }] }]"
                >
                    <a-select-option value=true>有</a-select-option>
                    <a-select-option value=false>无</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="房间数量" v-bind="formItemLayout">
                <a-input
                        autocomplete="off"
                    placeholder="请填写房间数量"
                    v-decorator="['roomNum', { rules: [{ required: true, message: '请输入房间数量' }] }]"
                />
            </a-form-item>
            <a-form-item label="入住人数" v-bind="formItemLayout">
                <a-input
                        autocomplete="off"
                    placeholder="请填写最大入住人数"
                    v-decorator="['peopleMax', { rules: [{ required: true, message: '请输入最大入住人数' }] }]"
                />
            </a-form-item>
            <a-form-item label="原始价格" v-bind="formItemLayout">
                <a-input
                        autocomplete="off"
                    placeholder="请填写原始价格"
                    v-decorator="['price', { rules: [{ required: true, message: '请输入原始价格' }] }]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';

    export default {
        name: 'addRoomModal',
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
            };
        },
        computed: {
            ...mapGetters([
                'addRoomModalVisible',
                'activeHotelId',
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'addRoomModal'});
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addRoomModalVisible',
                "set_allRoomModalVisible",
                'set_addRoomParams',
            ]),
            ...mapActions([
                'addRoom',
            ]),
            cancel() {
                this.set_addRoomModalVisible(false);
                this.set_allRoomModalVisible(true);
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            roomType: this.form.getFieldValue('roomType'),
                            price: Number(this.form.getFieldValue('price')),
                            total: Number(this.form.getFieldValue('roomNum')),
                            curNum: Number(this.form.getFieldValue('roomNum')),
                            breakfast: this.form.getFieldValue('breakfast'),
                            peopleMax: Number(this.form.getFieldValue('peopleMax')),
                            hotelId: this.activeHotelId,
                        };
                        this.set_addRoomParams(data);
                        this.addRoom();
                        this.form.resetFields();
                    }
                });
            },
        },
    };
</script>
