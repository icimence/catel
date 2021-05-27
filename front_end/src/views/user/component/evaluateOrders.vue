<template>
    <a-modal
            @cancel="cancelEvaluate"
            @ok="submitEvaluateResult"
            cancelText="取消"
            okText="提交"
            :visible="evaluateOrdersVisible"
            title="评价酒店"
    >
        <p class="hotelName">{{record.hotelName}}</p>
        <span>
            <a-rate :tooltips="desc" class="star" v-model="value"/>
        </span>
        <p class="please">请为我们的服务质量打分！</p>
        <!--div class="orderBorder">
            <p class="orderInfo">订单信息</p>
            <p class="infoType" v-if="this.record.roomType == 'BigBed'">房型：大床房</p>
            <p class="infoType" v-if="this.record.roomType == 'DoubleBed'">房型：双床房</p>
            <p class="infoType" v-if="this.record.roomType == 'Family'">房型：家庭房</p>
            <p class="infoPrice">共花费：￥{{this.record.price}}</p>
            <p class="infoDate">{{this.record.checkInDate}}  入住</p>
            <p class="infoDate">{{this.record.checkOutDate}}  退房</p>
        </div-->
        <a-textarea :rows="4" class="commonContext" placeholder="请输入您对我们的评价或建议" v-model="commentContext"/>
        <p class="thankText" v-if="this.thank">感谢您的评价！</p>

    </a-modal>

</template>

<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';

    export default {
        name: 'evaluateOrders',
        data() {
            return {
                form: this.$form.createForm(this, {name: 'evaluateOrders'}),
                value: 0,
                desc: ['我们下次一定会做得更好', '有待改进', '还不错', '基本满意', '好极了'],
            };

        },
        props: {
            record: {},
            thank: Boolean,
            commentContext: String,
        },
        computed: {
            ...mapGetters([
                'evaluateOrdersVisible',
                'currentPersonInfo',
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'evaluateOrdersVisible'});
        },
        mounted() {
        },
        methods: {
            ...mapMutations([
                'set_evaluateOrdersVisible',
                'set_addUserInfoParams',
                'set_currentPersonInfo',
            ]),
            ...mapActions([
                'updateUserInfo',
                'updatePersonInfo',
                'submitComment',
                'refreshOrder',
            ]),
            submitEvaluateResult() {
                this.thank = true;
                setTimeout(() => {
                    this.set_evaluateOrdersVisible(false);
                }, 1000);
                const data = {
                    hotelId: Number(this.record.hotelId),
                    score: Number(this.value),
                    userId: Number(this.record.userId),
                    title: '',
                    content: this.commentContext,
                    orderId: this.record.id,
                };
                this.submitComment(data);
                setTimeout(() => {
                    this.thank = false;
                    this.commentContext = '';
                    this.value = 0;
                    this.refreshOrder();
                }, 1000);
            },
            cancelEvaluate() {
                this.set_evaluateOrdersVisible(false);
                console.log('检查props是否生效');
                console.log(this.record);
                this.value = 0;
            },
        },
    };

</script>

<style scoped>
    .hotelName {
        font-size: 30px;
        text-align: center;
    }

    .star {
        transform: scale(2, 2);
        margin-left: 170px;
    }

    .orderInfo {
        margin-top: 30px;
        font-size: 20px;
        text-align: center;
    }

    .infoDate {
        text-align: center;
        margin-top: 20px;
    }

    .infoType {
        text-align: center;
        margin-top: 20px;
    }

    .infoPrice {
        text-align: center;
    }

    .orderBorder {
        border-style: solid;
        border-width: 1px;
        width: 150px;
        height: 250px;
        margin: 0 auto;
        margin-top: 20px;
    }

    .thankText {
        color: green;
        text-align: center;
        margin-top: 50px;
    }

    .please {
        text-align: center;
        margin-top: 20px;
    }

    .commonContext {
        margin-top: 20px;
    }
</style>
