<template>
  <div>
    <div>
      <h2>Please Log In</h2>
      <div id="loginForm">
        <form @submit.prevent="fnLogin">
          <p>
            <input class="w3-input" name="uid" placeholder="Enter your ID" v-model="accountId"><br>
          </p>
          <p>
            <input name="password" class="w3-input" placeholder="Enter your password" v-model="accountPwd" type="password">
          </p>
          <p>
            <button type="submit" class="w3-button w3-green w3-round">Login</button>
          </p>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions, mapGetters} from 'vuex'

export default {
  data() {
    return {
      accountId: '',
      accountPwd: ''
    }
  },
  methods: {
    ...mapActions(['login']),
    async fnLogin() {
      if (this.accountId === '') {
        alert('ID를 입력하세요.')
        return
      }

      if (this.accountPwd === '') {
        alert('비밀번호를 입력하세요.')
        return
      }
      try {
        let loginResult = await this.login({accountId: this.accountId, accountPwd: this.accountPwd})
        this.$store.state.isLogin = true
        this.$store.state.memberId = this.accountId
        if (loginResult) alert('로그인 결과 : ' + loginResult)
      } catch (err) {
        if (err.message.indexOf('Network Error') > -1) {
          alert('서버에 접속할 수 없습니다. 상태를 확인해주세요.')
        } else {
          alert('로그인 정보를 확인할 수 없습니다.')
        }
      }
      this.fnList()
    },
    fnList() {
      this.$router.push({
        path: '/post/list',
        query: {
          page: 0,
          keyword: '',
          category: 'title'
      }
      })
    }
  },
  computed: {
    ...mapGetters({
      errorState: 'getErrorState'
    })
  }
}
</script>

<style>
#loginForm {
  width: 500px;
  margin: auto;
}
</style>