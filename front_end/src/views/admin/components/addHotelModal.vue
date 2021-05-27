<template>
    <a-modal
            :visible="addHotelModalVisible"
            @cancel="cancel"
            @ok="handleSubmit"
            cancelText="取消"
            okText="确定"
            title="添加酒店"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="酒店名" @change="changeHotelName">
                <a-input
                        placeholder="请填写酒店名称"
                        autocomplete="off"
                        v-decorator="['name', { rules: [{ required: true, message: '请填写酒店名称' }] }]"
                />
            </a-form-item>
            <a-form-item label="信用值限制" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写最低要求的信用值"
                        autocomplete="off"
                        v-decorator="['creditBound', { rules: [{ required: true, message: '请输入酒店要求的最低信用值' }] }]"
                />
            </a-form-item>
            <a-form-item label="选定酒店管理员" v-bind="formItemLayout">
                <a-select @change="handleManagerChange" default-value="请选择酒店管理员" style="width: 128px" v-decorator="[
                        'hotelManager',
                        {
                            rules: [{ required: true, message: '请选择酒店管理员' }]
                        }
                    ]">
                    <a-select-option value="-1">
                        待定
                    </a-select-option>
                    <a-select-option :key="item.index" :value="item.id" v-for="item in managerList">
                        {{item.username}}
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="酒店地址" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写酒店地址"
                        autocomplete="off"
                        v-decorator="['address', { rules: [{ required: true, message: '请填写酒店地址' }] }]"
                />
            </a-form-item>
            <a-form-item label="酒店商圈" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                        'BizRegion',
                        { rules: [{ required: true, message: '请选择酒店商圈' }] }]"
                >
                    <a-select-option value="XiDan">
                        西单
                    </a-select-option>
                    <a-select-option value="DongDan">
                        东单
                    </a-select-option>
                    <a-select-option value="NanDan">
                        南单
                    </a-select-option>
                    <a-select-option value="BeiDan">
                        北单
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="酒店星级" v-bind="formItemLayout">
                <a-select
                        @change="changeStar"
                        v-decorator="[
                    'hotelStar',
                    { rules: [{ required: true, message: '请选择酒店星级' }] }]"
                >
                    <a-select-option value="Three">三星级</a-select-option>
                    <a-select-option value="Four">四星级</a-select-option>
                    <a-select-option value="Five">五星级</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="客服热线" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写联系手机号"
                        autocomplete="off"
                        v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' }] }]"
                />
            </a-form-item>
            <a-form-item label="酒店简介" v-bind="formItemLayout">
                <a-input
                        :rows="4"
                        placeholder="请填写酒店简介"
                        type="textarea"
                        v-decorator="['description', { rules: [{ required: true, message: '请填写酒店简介' }] }]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';

    export default {
        name: 'addHotelModal',
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
                managerChose: -1,
            };
        },
        computed: {
            ...mapGetters([
                'userId',
                'addHotelModalVisible',
                "managerList"
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'addHotelModal'});
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addHotelParams',
                'set_addHotelModalVisible',
            ]),
            ...mapActions([
                'addHotel',
                "getManagerList"
            ]),
            cancel() {
                this.set_addHotelModalVisible(false);
            },
            changeStar(v) {

            },
            changeHotelName(){
                this.getManagerList();
            },
            handleManagerChange(v){
              this.managerChose = Number(v);
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            name: this.form.getFieldValue('name'),
                            managerId: this.managerChose,
                            creditBound: this.form.getFieldValue('creditBound'),
                            description: this.form.getFieldValue('description'),
                            address: this.form.getFieldValue('address'),
                            phoneNumber: this.form.getFieldValue('phoneNumber'),
                            hotelStar: this.form.getFieldValue('hotelStar'),
                            bizRegion:this.form.getFieldValue('BizRegion'),
                        };
                        this.set_addHotelParams(data);
                        this.addHotel();
                    }
                });
            },
        },
    };
</script>
