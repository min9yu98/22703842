<template>
    <div class="post-detail">
      <div class="common-buttons">
        <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnUpdate">수정</button>&nbsp;
        <button type="button" class="w3-button w3-round w3-blue-red" v-on:click="fnDelete">삭제</button>&nbsp;
        <button type="button" class="w3-button w3-round w3-gray" v-on:click="fnList">목록</button>&nbsp;
      </div>
      <div class="post-contents">
        <h3>제목 : {{ title }}</h3>
        <div>
          <span>작성자 : {{ writerAccountId }}</span>
          <br>
          <span>작성일 : {{ createdAt }}  /  조회수 : {{ viewCount }}</span>
        </div>
      </div>
      <div class="post-contents">
        <span>{{ content }}</span>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        requestBody: this.$route.query,
        postId: this.$route.query.postId,
        title: '',
        writerAccountId: '',
        createdAt: '',
        viewCount: '',
        content: ''
      }
    },
    mounted() {
      this.fnGetView()
    },
    methods: {
      fnGetView() {
        try {
            this.$axios.get(this.$serverUrl + "/posts/" + this.postId, {
                params: this.requestBody
            }).then((res) => {
                if (res.data.code === 1000) {
                    console.log(res)
                    const post = res.data.data
                    this.title = post.title
                    this.writerAccountId = post.writerAccountId
                    this.createdAt = post.createdAt
                    this.viewCount = post.viewCount
                    this.content = post.content
                }
            })
        } catch (err) {
            alert('데이터를 불러오는데 실패했습니다.')
        }
      },
      fnList() {
        delete this.requestBody.postId
        this.$router.push({
          path: './list',
          query: this.requestBody
        })
      },
      fnUpdate() {
        this.$router.push({
          path: './write',
          query: this.requestBody
        })
      },
      fnDelete() {
        if (!confirm('삭제하겠습니까?')) return
  
        this.$axios.delete(this.$serverUrl + '/posts/' + this.postId).then(() => {
          alert('삭제되었습니다.')
          this.fnList()
        }).catch((err) => {
          console.log(err)
        })
      }
    }
  }
  </script>
  
  <style scoped>
  </style>