<template>
    <div v-if="orderData" class="info-block">
        <div class="main-info">
            <div class="sender-info">
                <div class="name">{{ orderData.senderName }}</div>
                <div class="phone">电话：{{ orderData.senderPhoneNumber }}</div>
                <div class="address">地址：{{ orderData.senderAddress }}</div>
            </div>
            <div class="arrow">→</div>
            <div class="receiver-info">
                <div class="name">{{ orderData.receiverName }}</div>
                <div class="phone">电话：{{ orderData.receiverPhoneNumber }}</div>
                <div class="address">地址：{{ orderData.receiverAddress }}</div>
            </div>

            <div class="cargo-info">
                <p>货物名称：{{ orderData.cargoName }}</p>
                <p>货物描述：{{ orderData.cargoInfo }}</p>
                <p>紧急程度：
                    <span v-if="orderData.urgentLevel === 1">低</span>
                    <span v-else-if="orderData.urgentLevel === 2">中</span>
                    <span v-else-if="orderData.urgentLevel === 3">高</span>
                </p>
                <p>是否冷藏：{{ orderData.refrigerated ? "是" : "否" }}</p>
            </div>
            
        </div>
    <div class="order-id">运单号: {{ orderData.orderId }}</div>
    </div>
</template>
  
<script>
export default {
    name: 'InfoBlock',
    props: {
        orderData: {
            type: Object,
            default: () => ({}),
        },
    },
};
</script>
  
<style scoped>
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
    margin-left: auto;
    font-size: 18px;
}

.cargo-info p {
    margin-bottom: 5px;
}

.order-id {
    margin-left: 0px;
    font-size: 14px;
    color: #999999;
}
</style>

