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
        async fnLogout() {
            try{
                const token = localStorage.getItem("user_token")
                if (!token) {
                    alert('이미 로그아웃 되었거나 세션이 만료되었습니다.');
                    this.$router.push('/member/login');
                    return;
                }
                await this.$axios.post(this.$serverUrl + "/members/logout", null, {
                    headers: {
                        'Authorization': token
                    }
                })
                this.$store.commit('logout')
                localStorage.clear()
                this.$router.push('/member/login');
            } catch (err) {
                console.error('로그아웃 에러:', err)
                this.$store.commit('logout');
                localStorage.clear();
                alert('로그아웃 처리 중 오류가 발생했습니다.')
            }
        }
    }
}
</script>

<style scoped>

</style>