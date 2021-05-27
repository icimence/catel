<template>
    <a-modal
        :closable="false"
        :visible="manageHotelVisible"
        title="修改信息"

    >
        <a-form :form="form">
            <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }" label="酒店名">
                <a-input
                        autocomplete="off"
                    placeholder="请填写酒店名"
                    v-decorator="[ 'name' , { rules: [{ required: true, message: '请输入酒店名' }],initialValue: this.currentHotelInfo.name }]"
                    v-if="modify"
                />
                <span v-else>{{this.currentHotelInfo.name}}</span>
            </a-form-item>
            <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }" label="酒店图片" v-if="modify">
                <!--                基本照搬，然后name和class以及下面img里面的alt改了一下-->
                <a-upload
                    :before-upload="beforeUpload"
                    :show-upload-list="false"
                    @change="handleChange"
                    action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
                    class="pic-uploader"
                    list-type="picture-card"
                    name="pic"
                >
                    <img :src="this.imageUrl" alt="pic" class="pic-prev" v-if="imageUrl" />
                    <div v-else>
                        <a-icon :type="loading ? 'loading' : 'plus'" />
                        <div class="ant-upload-text">
                            Upload
                        </div>
                    </div>
                </a-upload>
            </a-form-item>
            <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }" label="商圈">
                <!--                <a-input-->
                <!--                        placeholder="请填写酒店名"-->
                <!--                        v-decorator="[ 'bizRegion' , { rules: [{ required: true, message: '请输入商圈地址' }],initialValue: this.currentHotelInfo.bizRegion }]"-->
                <!--                        v-if="modify"-->
                <!--                />-->
                <span>{{ this.currentHotelInfo.bizRegion}}</span>
            </a-form-item>
            <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 10, offset: 1  }" label="地址">
                <a-input
                        autocomplete="off"
                    placeholder="请填写地址"
                    v-decorator="[ 'address' , { rules: [{ required: true, message: '请输入地址' }],initialValue: this.currentHotelInfo.address }]"
                    v-if="modify"
                />
                <span v-else>{{this.currentHotelInfo.address}}</span>
            </a-form-item>
            <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }" label="酒店星级">
                <span v-if="this.currentHotelInfo.hotelStar === 'Five'">五星级</span>
                <span v-if="this.currentHotelInfo.hotelStar === 'Four'">四星级</span>
                <span v-if="this.currentHotelInfo.hotelStar === 'Three'">三星级</span>
                <span v-if="this.currentHotelInfo.hotelStar === 'Two'">二星级</span>
                <span v-if="this.currentHotelInfo.hotelStar === 'One'">一星级</span>
            </a-form-item>
            <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }" label="评分">
                <span>{{ this.currentHotelInfo.rate}}</span>
            </a-form-item>
            <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 20, offset: 1  }" label="简介">
                <a-textarea
                    :rows="4"
                    placeholder="请填写简介"
                    v-decorator="[ 'description' , { rules: [{ required: true, message: '请输入简介' }],initialValue:this.currentHotelInfo.description }]"
                    v-if="modify"
                />
                <span v-else>{{this.currentHotelInfo.description}}</span>
            </a-form-item>
        </a-form>
        <template slot="footer">
            <a-button @click="saveModifyHotel" type="primary" v-if="modify">保存</a-button>
            <a-button @click="modifyHotel" type="primary" v-else>修改</a-button>
            <a-button @click="cancelSaveModify" type="info" v-if="modify">取消</a-button>
            <a-button @click="cancelModifyHotel" type="info" v-else>完成</a-button>
        </template>

    </a-modal>

</template>

<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';

    //基本照搬
    function getBase64(img, callback) {
        const reader = new FileReader();
        reader.addEventListener('load', () => callback(reader.result));
        reader.readAsDataURL(img);
    }

    export default {
        name: 'modifyHotelInfo',
        data() {
            return {
                modify: false,
                imageUrl: '',//添加的部分
                form: this.$form.createForm(this, {name: 'modifyHotelInfo'}),
            };

        },
        computed: {
            ...mapGetters([
                'hotelListMatchManager',
                'manageHotelVisible',
                'currentHotelInfo',
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'modifyHotelInfo'});
        },
        mounted() {
            //this.imageUrl=this.currentHotelInfo.pic;//添加的部分
        },
        methods: {
            ...mapMutations([
                'set_hotelInfoParams',
                'set_manageHotelVisible',
            ]),
            ...mapActions([
                'updateHotelInfo',
            ]),
            modifyHotel() {
                // console.log(this.currentHotelInfo.id)
                // console.log(this.currentHotelId)
                // console.log(this.currentHotelInfo.name)
                this.imageUrl = this.currentHotelInfo.pic;//添加的部分
                this.form.setFieldsValue({
                    'name': this.currentHotelInfo.name,
                    'bizRegion': this.currentHotelInfo.bizRegion,
                    'address': this.currentHotelInfo.address,
                    'description': this.currentHotelInfo.description,
                });
                this.modify = true;
            },
            cancelModifyHotel() {
                this.modify = false;
                this.set_manageHotelVisible(false);
            },
            saveModifyHotel() {
                // this.imageUrl=this.currentHotelInfo.pic
                //console.log(this.form.getFieldValue('name'))
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            id: this.currentHotelInfo.id,
                            name: this.form.getFieldValue('name'),
                            bizRegion: this.form.getFieldValue('bizRegion'),
                            address: this.form.getFieldValue('address'),
                            description: this.form.getFieldValue('description'),
                            pic: this.imageUrl.substring(this.imageUrl.indexOf(',') + 1),
                        };
                        this.set_hotelInfoParams(data);
                        this.updateHotelInfo();
                        this.modify = false;
                        this.form.resetFields();
                    }
                    // location.reload()
                });
            },
            cancelSaveModify() {
                this.modify = false;
            },
            handleChange(info) {
                if (info.file.status === 'uploading') {
                    this.loading = true;
                    return;
                }
                if (info.file.status === 'done') {
                    // Get this url from response in real world.
                    getBase64(info.file.originFileObj, imageUrl => {
                        // this.imageUrl = imageUrl;
                        this.loading = false;
                        //这里略微改动，在hotel.js的hotelinfoParams里加了pic字段
                        this.imageUrl = imageUrl;
                        //location.reload()
                        // this.$router.go(0)
                    });
                }
            },
            //基本照搬
            beforeUpload(file) {
                const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
                if (!isJpgOrPng) {
                    this.$message.error('You can only upload IMG file!');
                }
                const isLt2M = file.size / 1024 / 1024 < 2;
                if (!isLt2M) {
                    this.$message.error('Image must smaller than 2MB!');
                }
                return isJpgOrPng && isLt2M;
            },
        },
    };

</script>

<style>
    /*基本照搬*/
    .avatar-uploader > .ant-upload {
        width: 128px;
        height: 128px;
    }

    .pic-prev {
        width: 128px;
        height: 128px;
    }

    .ant-upload-select-picture-card i {
        font-size: 32px;
        color: #999;
    }

    .ant-upload-select-picture-card .ant-upload-text {
        margin-top: 8px;
        color: #666;
    }

</style>
