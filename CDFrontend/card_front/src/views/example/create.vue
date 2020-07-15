<template>
  <article-detail :is-edit="false" />
</template>

<script>
import ArticleDetail from '../../components/edit/MailEditPanel'

export default {
  name: 'CreateArticle',
  components: { ArticleDetail }
}
</script>

