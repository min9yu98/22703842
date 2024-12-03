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
        async fnSave() {
            let apiUrl = this.$serverUrl + "/posts"
            this.form = {
                "postId": this.postId,
                "title": this.title,
                "content": this.content,
                "writerAccountId": this.writerAccountId,
                "createdAt": this.createdAt,
                "viewCount": this.viewCount
            }

            try {
                let response;
                if (this.postId === undefined) {
                    // 새 게시글 작성
                    response = await this.$axios.post(apiUrl, this.form);
                    if (response.data.code === 1000) {
                        const savedPostId = response.data.data.postId;
                
                        // content에서 base64 형식의 이미지/비디오 데이터 추출
                        const files = this.extractFilesFromContent();
                        
                        // 파일이 있다면 업로드
                        if (files.length > 0) {
                            for (const file of files) {
                                const formData = new FormData();
                                formData.append('file', file);
                                
                                await this.$axios.post(
                                    `${this.$serverUrl}/posts/files/${savedPostId}`,
                                    formData,
                                    {
                                        headers: {
                                            'Content-Type': 'multipart/form-data'
                                        }
                                    }
                                );
                            }
                        }
                        alert('저장되었습니다.');
                        this.fnView(savedPostId);
                    }
                } else {
                    // 게시글 수정
                    response = await this.$axios.patch(apiUrl, this.form);
                    if (response.data.code === 1000) {
                        // 수정 시에도 동일한 파일 업로드 로직 적용
                        const files = this.extractFilesFromContent();
                
                        if (files.length > 0) {
                            for (const file of files) {
                                const formData = new FormData();
                                formData.append('file', file);
                                
                                await this.$axios.post(
                                    `${this.$serverUrl}/posts/files/${this.postId}`,
                                    formData,
                                    {
                                        headers: {
                                            'Content-Type': 'multipart/form-data'
                                        }
                                    }
                                );
                            }
                        }
                
                        alert('수정되었습니다.');
                        this.fnView(this.postId);
                    }
                }
            } catch (err) {
                console.error('저장 실패:', err);
                alert('저장에 실패했습니다.');
            }
        },
        // content에서 파일 데이터 추출하는 메서드
        extractFilesFromContent() {
            const files = [];
            const tempDiv = document.createElement('div');
            tempDiv.innerHTML = this.content;

            // 이미지 파일 추출
            const images = tempDiv.getElementsByTagName('img');
            for (const img of images) {
                const src = img.getAttribute('src');
                if (src && src.startsWith('data:')) {
                    // base64 데이터를 파일로 변환
                    const file = this.dataURLtoFile(src, 'image.png');
                    files.push(file);
                }
            }

            // 비디오 파일 추출
            const videos = tempDiv.getElementsByTagName('video');
            for (const video of videos) {
                const src = video.getAttribute('src');
                if (src && src.startsWith('data:')) {
                    // base64 데이터를 파일로 변환
                    const file = this.dataURLtoFile(src, 'video.mp4');
                    files.push(file);
                }
            }

            return files;
        },

        // base64 데이터를 File 객체로 변환하는 메서드
        dataURLtoFile(dataurl, filename) {
            const arr = dataurl.split(',');
            const mime = arr[0].match(/:(.*?);/)[1];
            const bstr = atob(arr[1]);
            let n = bstr.length;
            const u8arr = new Uint8Array(n);
            
            while(n--) {
                u8arr[n] = bstr.charCodeAt(n);
            }
            
            return new File([u8arr], filename, {type: mime});
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
