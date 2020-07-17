<template>
  <article-detail :is-edit="true" />
</template>

<script>
import ArticleDetail from '../../components/edit/MailEditPanel'

export default {
  name: 'EditForm',
  components: { ArticleDetail }
}
</script>

