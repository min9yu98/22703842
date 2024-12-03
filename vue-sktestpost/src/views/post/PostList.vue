<template>
    <div class="post-list">
      <div class="common-buttons" v-if="isLogin">
        <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnWrite">등록</button>
      </div>
      <table class="w3-table-all">
        <thead>
          <tr>
            <th>제목</th>
            <th>작성자 ID</th>
            <th>조회수</th>
            <th>첨부파일여부</th>
            <th>등록일시</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="row in list" :key="row.postId">
            <td><a @click="fnView(row.postId)">{{ row.title }}</a></td>
            <td><a @click="fnView(row.postId)">{{ row.writerAccountId }}</a></td>
            <td><a @click="fnView(row.postId)">{{ row.postViewCount }}</a></td>
            <td><a @click="fnView(row.postId)">{{ row.postFileState ? 'O' : 'X' }}</a></td>
            <td><a @click="fnView(row.postId)">{{ row.createdAt }}</a></td>
          </tr>
        </tbody>
      </table>
      <div class="pagination w3-bar w3-padding-16 w3-small" v-if="paging.pageCount > 1">
        <span class="pg">
          <a href="javascript:;" 
             @click="fnPage(0)" 
             class="first w3-button w3-bar-item w3-border"
             :class="{ 'w3-disabled': paging.pageNumber === 0 }">
            &lt;&lt;
          </a>
          
          <a href="javascript:;" 
             v-if="paging.pageNumber > 0" 
             @click="fnPage(paging.pageNumber - 1)"
             class="prev w3-button w3-bar-item w3-border">
            &lt;
          </a>
          
          <template v-for="n in paging.pageCount" :key="n-1">
            <strong v-if="paging.pageNumber === (n-1)" 
                    class="w3-button w3-bar-item w3-border w3-green">
              {{ n }}
            </strong>
            <a v-else 
               class="w3-button w3-bar-item w3-border" 
               href="javascript:;" 
               @click="fnPage(n-1)">
              {{ n }}
            </a>
          </template>
          
          <a href="javascript:;" 
             v-if="paging.pageNumber < paging.pageCount - 1" 
             @click="fnPage(paging.pageNumber + 1)"
             class="next w3-button w3-bar-item w3-border">
            &gt;
          </a>
          
          <a href="javascript:;" 
             @click="fnPage(paging.pageCount - 1)"
             class="last w3-button w3-bar-item w3-border"
             :class="{ 'w3-disabled': paging.pageNumber === paging.pageCount - 1 }">
            &gt;&gt;
          </a>
        </span>
      </div>
    </div>
    <div>
  <select v-model="category">
    <option value="">- 선택 -</option>
    <option value="member_account_id">작성자ID</option>
    <option value="title">제목</option>
  </select>
  &nbsp;
  <input type="text" v-model="keyword" @keyup.enter="fnPage()">
  &nbsp;
  <button @click="fnGetList()">검색</button>
</div>
</template>
  
<script>
export default {
  computed: {
    isLogin() {
      return this.$store.state.isLogin;
    }
  },
data() {
    return {
        list: [],
        requestBody: {
            page: 0,
            keyword: this.$route.query.keyword ? this.$route.query.keyword : '',
            category: this.$route.query.category ? this.$route.query.category : 'title'
        },
        paging: {
            pageNumber: 0,
            pageCount: 0
        },
        keyword: this.$route.query.keyword ? this.$route.query.keyword : '',
        category: this.$route.query.category ? this.$route.query.category : 'title'
    }
},
methods: {
    async fnGetList() {
      try {
        const response = await this.$axios.get(this.$serverUrl + "/posts", {
          params: {
            page: this.requestBody.page,
            keyword: this.keyword,
            category: this.category
          }
        })
        console.log('API 요청 파라미터:', response.config.params)

        if (response.data.code === 1000) {
            const responseData = response.data.data
            this.list = responseData.postList
            this.paging = {
                pageNumber: responseData.pageNumber,
                pageCount: responseData.pageCount
          }
          console.log('갱신된 리스트 데이터:', this.list)
        }
      } catch (err) {
        console.error('API 에러:', err)
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
    },
    getPageNumbers() {
      const pageNumbers = []
      for (let i = 0; i < this.paging.pageCount; i++) {
        pageNumbers.push(i)
      }
      return pageNumbers
    },
    fnPage(n) {
      if (n >= 0 && n < this.paging.pageCount) {
        this.requestBody.page = n
        this.paging.pageNumber = n
        this.fnGetList()
      }
    }
  },
  created() {
    this.fnGetList()
  },
}
</script>