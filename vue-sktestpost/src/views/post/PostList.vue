<template>
    <div class="post-list">
      <div class="common-buttons">
        <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnWrite">등록</button>
      </div>
      <table class="w3-table-all">
        <thead>
          <tr>
            <th>제목</th>
            <th>작성자 ID</th>
            <th>조회수</th>
            <th>첨부파일여부</th>
            <th>등록일</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="row in list" :key="row.postId">
            <td><a @click="fnView(`${row.postId}`)">{{ row.title }}</a></td>
            <td>{{ row.writerAccountId }}</td>
            <td>{{ row.postViewCount }}</td>
            <td>{{ row.postFileState ? 'O' : 'X' }}</td>
            <td>{{ row.createdAt }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        list: [],
        requestBody: {},
        paging: {
          pageNumber: 0,
          pageCount: 0
        }
      }
    },
    methods: {
      async fnGetList() {
        try {
          const response = await this.$axios.get(this.$serverUrl + "/posts")
          console.log('API 응답:', response.data) // 디버깅용
          
          if (response.data.code === 1000) {
            this.list = response.data.data.postList
            console.log('게시글 목록:', this.list) // 디버깅용
          }
        } catch (err) {
          alert('데이터를 불러오는데 실패했습니다.')
        }
      },
      fnView(postId) {
        this.$router.push({
          path: './detail',
          query: {
            postId: postId
          }
        })
      },
      fnWrite() {
        this.$router.push({
          path: './write'
        })
      }
    },
    created() {
      this.fnGetList()
    }
  }
  </script>