<template>
  <article-detail :is-edit="false" />
</template>

<script>
import ArticleDetail from '../../components/article/MailEditPanel'

export default {
  name: 'CreateArticle',
  components: { ArticleDetail }
}
</script>

