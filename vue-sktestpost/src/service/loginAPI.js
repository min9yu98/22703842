// src/service/loginAPI.js
import axios from 'axios'

const getMemberInfo = (accountId, accountPwd) => {
    const reqData = {
      'accountId': accountId,
      'accountPwd': accountPwd
    }
    let serverUrl = '//localhost:8080'

    return axios.post(serverUrl + "/members/login", reqData, {
        headers: {
            'Content-Type': 'application/json'
        }
    })
  }
  
  export default {
    async doLogin(accountId, accountPwd) {
      try {
        const getMemberInfoPromise = getMemberInfo(accountId, accountPwd)
        const [memberInfoResponse] = await Promise.all([getMemberInfoPromise])
        if (memberInfoResponse.data.length === 0) {
          return 'notFound'
        } else {
            localStorage.setItem('user_token', memberInfoResponse.headers['access-token'])
            return memberInfoResponse
        }
      } catch (err) {
        console.error(err)
      }
    }
  }