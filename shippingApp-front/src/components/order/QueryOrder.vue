<template>
    
        <div id="query-order" class="total-css">
            <top-bar :userType="'no'"></top-bar>
            <div class="search-container">
                <input type="text" class="input" placeholder="请输入订单号" v-model="orderId" @keyup.enter="searchOrder" />
                <button class="button" @click="searchOrder">查询运单</button>
            </div>
            <div v-if="orderData" class="info-block">
                <div class="main-info">
                    <div class="sender-info">
                        <div class="name">{{ orderData.senderName }}</div>
                        <div class="phone">电话：{{ orderData.senderPhoneNumber }}</div>
                        <div class="address">地址：{{ orderData.senderAddress }}</div>
                    </div>
                    <div class="arrow">-></div>
                    <div class="receiver-info">
                        <div class="name">{{ orderData.receiverName }}</div>
                        <div class="phone">电话：{{ orderData.receiverPhoneNumber }}</div>
                        <div class="address">地址：{{ orderData.receiverAddress }}</div>
                    </div>

                    <div class="cargo-info">
                        <p>货物名称：{{ orderData.cargoName }}</p>
                        <p>货物描述：{{ orderData.cargoInfo }}</p>
                        <p>紧急程度：{{ orderData.urgentLevel }}</p>
                        <p>是否冷藏：{{ orderData.refrigerated ? "是" : "否" }}</p>
                    </div>
                </div>

            </div>
        </div>
</template>
  
<script>
import axios from "axios";
import TopBar from '../TopBar.vue'
export default {
    components: {
    TopBar
  },
    data() {
        return {
            orderId: "",
            orderData: null,
        };
    },
    methods: {
        async searchOrder() {
            if (!this.orderId) {
                alert("请输入订单号");
                return;
            }
            try {
                const response = await axios.get(
                    `/order/get-order-by-order-id?orderId=${this.orderId}`
                );
                this.orderData = response.data.data.order;
                if ((this.orderData) === null) {
                    alert("查询失败，请检查号码后重试");
                }
            } catch (error) {
                alert("查询失败，请稍后重试");
            }
        },
    },
};
</script>
  
<style scoped>

.total-css {
    padding-top: 60px;
}
.search-container {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 30px;
    max-width: 800px;
    margin: 0 auto;

}

.input {
    flex-grow: 1;
    background: transparent;
    border: none;
    border-bottom: 3px solid #272343;
    font-size: 24px;
    outline: none;
}

.input:focus {
    border-color: #ffd803;
    color: #272343;
}

.button {
    background-color: #ffd803;
    color: #272343;
    border: none;
    border-radius: 3px;
    padding: 15px 50px;
    cursor: pointer;
    font-size: 15px;
    margin-left: 15px;
}

.button:hover {
    background-color: #ffca1e;
}

.info-block {
    width: 100%;
    max-width: 600px;
    background-color: #fffffe;
    padding: 20px;
    margin: 20px auto;
    border-radius: 10px;
}

.main-info {
    display: flex;
    align-items: center;
    font-size: 40px;
    margin-bottom: 10px;
}

.sender-info,
.receiver-info {
    display: flex;
    flex-direction: column;
}

.arrow {
    margin: 0 20px;
}

.name {
    font-weight: bold;
}

.phone,
.address {
    font-size: 18px;
    margin-top: 5px;
}

.cargo-info {
    margin-left: 30px;
    font-size: 18px;
}

.cargo-info p {
    margin-bottom: 5px;
}
</style>
   
  