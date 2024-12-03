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
            <quill-editor
                :disabled="false"
                :value="content"
                :options="editorOption"
                @change="onEditorChange"
                @blur="onEditorBlur($event)"
                @focus="onEditorFocus($event)"
                @ready="onEditorReady($event)"
            />
        </div>
    </div>
</template>

<script>
import hljs from "highlight.js";
import debounce from "lodash/debounce";

// highlight.js style
import "highlight.js/styles/tomorrow.css";

// import theme style
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";

export default {
    data() {
        return {
            editorOption: {
                placeholder: "내용을 입력하세요...",
                modules: {
                    toolbar: [
                        ["bold", "italic", "underline", "strike"],
                        ["blockquote", "code-block"],
                        [{ header: 1 }, { header: 2 }],
                        [{ list: "ordered" }, { list: "bullet" }],
                        [{ script: "sub" }, { script: "super" }],
                        [{ indent: "-1" }, { indent: "+1" }],
                        [{ direction: "rtl" }],
                        [{ size: ["small", false, "large", "huge"] }],
                        [{ header: [1, 2, 3, 4, 5, 6, false] }],
                        [{ font: [] }],
                        [{ color: [] }, { background: [] }],
                        [{ align: [] }],
                        ["link", "image", "video"],
                    ],
                    syntax: {
                        highlight: (text) => hljs.highlightAuto(text).value,
                    },
                },
            },
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
        onEditorChange: debounce(function(value) {
            this.content = value.html;
        }, 466),
        onEditorBlur(editor) {
            console.log("editor blur!", editor);
        },
        onEditorFocus(editor) {
            console.log("editor focus!", editor);
        },
        onEditorReady(editor) {
            console.log("editor ready!", editor);
        },
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
<style>
.post-detail {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
}

.post-contents {
    margin-bottom: 20px;
}

/* Quill 에디터 관련 스타일 */
.quill-editor {
    height: 400px;
}

.ql-container {
    height: calc(100% - 42px) !important;
}

.ql-editor {
    height: 100%;
    min-height: 300px;
    padding: 12px 15px;
}

.ql-container.ql-snow {
    border: 1px solid #ccc;
}

.ql-toolbar.ql-snow {
    border: 1px solid #ccc;
    border-bottom: none;
}

/* 에디터 내용 영역 스타일 */
.ql-editor p {
    margin-bottom: 0.5em;
}

/* 로딩 인디케이터 숨기기 */
.ql-container.ql-loading {
    visibility: hidden;
}
</style>
