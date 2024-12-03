<!-- PageHeader vue -->
<template>
    <header>
        <div id="header-title">
            <h1>게시판</h1>
        </div>
        <div id="nav">
            <router-link to="/">Home</router-link> |
            <router-link to="/post/list">게시판</router-link> |
            <template v-if="!this.$store.state.isLogin">
                <router-link to="/member/login">로그인</router-link>
            </template>
            <template v-else>
                <a href="javascript:;" @click="fnLogout">로그아웃</a>
            </template>
        </div>
    </header>
    <hr/>
</template>

<script>
export default {
    methods: {
        fnLogout() {
            const token = localStorage.getItem("user_token")
            console.log("token : " + token)
            this.$axios.post(this.$serverUrl + "/members/logout", null, {
                headers: {
                    'Authorization': token
                }
            }).then(() => {
                this.$store.state.isLogin = false
                localStorage.removeItem("user_token")
                location.reload()
            }).catch((err) => {
                console.error('로그아웃 에러:', err)
                alert('로그아웃 처리 중 오류가 발생했습니다.')
            })
        }
    }
}
</script>

<style scoped>

</style>