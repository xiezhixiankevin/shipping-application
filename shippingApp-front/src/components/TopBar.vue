<template>
    <div class="top-bar">
        <div class="title">Freight Master</div>
        <div class="username" @click="goToProfile">主页</div>
    </div>
</template>
  
<script>
export default {
    props: {
        username: {
            type: String,
            default: "Guest"
        },
        userType: {
            type: String,
            default: "User"
        }
    },
    methods: {
        goToProfile() {
            if (this.userType === "User") {
                this.goToUserDashboard();
            } else if (this.userType === "Carrier") {
                this.goToCarrierDashboard();
            }
        },
        goToUserDashboard() {
            this.$router.push({ path: "/user/dashboard" });
        },
        goToCarrierDashboard() {
            this.$router.push({ path: "/carrier/dashboard" });
        }
    }
};
</script>
  
<style scoped>
.top-bar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #ffd803;
    padding: 0 40px;
    height: 60px;
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 100;
}

.title {
    font-size: 25px;
    color: #272343;
    padding-left: 20px;
    font-weight: bold;
}

.username {
    cursor: pointer;
    font-size: 18px;
    color: #272343;
    padding-right: 40px;
    font-weight: bold;
}
</style>
  