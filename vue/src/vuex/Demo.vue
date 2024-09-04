<template>
</template>

<script>
import {mapState,mapMutations} from 'vuex'
export default {
    data() {
        return {

        }
    },
    computed: mapState(["count","msg","flag"]),
    methods: mapMutations(["add","reduce"]),
}
</script>
