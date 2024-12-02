<template>
    <div class="post-detail">
        <div class="common-buttons">
            <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnSave">저장</button>&nbsp;
            <button type="button" class="w3-button w3-round w3-gray" v-on:click="fnList">목록</button>&nbsp;
        </div>
        <div class="post-contents">
            <input type="text" v-model="title" class="w3-input w3-border" placeholder="제목을 입력하세요.">
        </div>
        <div class="post-contents">
            <textarea id="" cols="30" rows="10" v-model="content" class="w3-input w3-border" style="resize: none;"></textarea>
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
            this.$axios.get(this.$serverUrl + '/posts/' + this.postId, {
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
            }).catch((err) => {
                console.log(err)
            })
        },
        fnList() {
            delete this.requestBody.postId
            this.$router.push({
                path: './list',
                query: this.requestBody
            })
        },
        fnView(postId) {
            this.$router.push({
            path: './detail',
            query: {
                    postId: postId
                }
            })
        },
        fnSave() {
            let apiUrl = this.$serverUrl + "/posts"
            this.form = {
                "postId": this.postId,
                "title": this.title,
                "content": this.content,
                "writerAccountId": this.writerAccountId,
                "createdAt": this.createdAt,
                "viewCount": this.viewCount
            }

            if (this.postId === undefined) {
                this.$axios.post(apiUrl, this.form).then((res) => {
                    if (res.data.code === 1000) {
                        alert('저장되었습니다.')
                        this.fnView(res.data.data.postId)
                    }
                }).catch((err) => {
                    console.log(err)
                })
            } else {
                this.$axios.patch(apiUrl, this.form).then((res) => {
                    if (res.data.code === 1000) {
                        alert('저장되었습니다.')
                        this.fnView(res.data.data.postId)
                    }
                }).catch((err) => {
                    console.log(err)
                })
            }
        }

    }
}
</script>
<style scoped>
</style>
