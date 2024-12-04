<template>
  <PageHeader/>
  <router-view/>
  <PageFooter/>
</template>

<script>
import PageHeader from "@/components/PageHeader"
import PageFooter from "@/components/PageFooter"

export default {
  name: 'App',
  components: {
    PageFooter,
    PageHeader
  },
  data() {
    return {
      isRefreshing: false
    }
  },
  created() {
    window.addEventListener('beforeunload', this.handleBeforeUnload);
    document.addEventListener('visibilitychange', this.handleVisibilityChange);
    window.addEventListener('keydown', this.handleKeyDown);
  },
  unmounted() {
    window.removeEventListener('beforeunload', this.handleBeforeUnload);
    document.removeEventListener('visibilitychange', this.handleVisibilityChange);
    window.removeEventListener('keydown', this.handleKeyDown);

  },
  methods: {
    handleKeyDown(event) {
      if (event.key === 'F5' || (event.ctrlKey && event.key === 'r') || (event.metaKey && event.key === 'r')) {
        console.log("새로고침 키 감지");
        this.isRefreshing = true;
      }
    },
    handleBeforeUnload(event) {
      if (this.$store.state.isLogin) {
        this.isRefreshing = true;  // 새로고침 시에도 beforeunload가 발생하므로 플래그 설정
        event.preventDefault();
      }
    },
    handleVisibilityChange() {
      console.log("Visibility changed - hidden:", document.hidden);
      console.log("isRefreshing:", this.isRefreshing);

      // 페이지가 숨겨지고, 새로고침이 아닐 때만 로그아웃
      if (!this.isRefreshing && this.$store.state.isLogin) {
        const token = localStorage.getItem("user_token");
        if (token) {
          fetch(`${this.$serverUrl}/members/logout`, {
            method: 'POST',
            headers: {
              'Authorization': token,
              'Content-Type': 'application/json'
            },
          }).then(() => {
            this.$store.commit('logout');
            localStorage.clear();
          }).catch(err => {
            console.error('로그아웃 처리 중 오류 발생:', err);
          });
        }
      }

      // visibility가 변경될 때마다 새로고침 플래그 초기화
      if (!document.hidden) {
        this.isRefreshing = false;
      }
    }
  }
}


</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
#nav {
  padding: 30px;
}
#nav a {
  font-weight: bold;
  color: #2c3e50
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
