<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'

interface GetSchool {
  id: number,
  name: string,
}

interface PostSchool {
  name: string,
}

const schools = ref([] as GetSchool[])
const newSchool = ref<PostSchool>({
  name: ''
})

async function fetchSchools() {
  try {
    const response = await axios.get<GetSchool[]>('http://localhost:8080/schools')
    schools.value = response.data
  } catch (error) {
    console.error('Failed to fetch schools:', error)
  }
}

async function addSchool() {
  try {
    const response = await axios.post<GetSchool>('http://localhost:8080/schools', newSchool.value)
    schools.value.push(response.data)
    newSchool.value = { name: '' }
  } catch (error) {
    console.error('Failed to add school:', error)
  }
}

onMounted(async () => {
  await fetchSchools()
})
</script>


<template>
  <div class="m-4 space-y-6">
    <div>
      <h3 class="text-2xl font-semibold">Schools</h3>
    </div>

    <div>
      <ul class="space-y-2">
        <li v-for="(school, index) in schools" :key="index" style="list-style-type: none;"
          class="p-4 bg-white border rounded-lg shadow-md hover:bg-gray-50">
          {{ index + 1 }}. {{ school.name }}
        </li>
      </ul>
    </div>

    <div class="p-4 mt-8 bg-gray-100 border rounded-lg">
      <h4 class="mb-4 text-lg font-medium">Add Student</h4>
      <form @submit.prevent="addSchool">
        <div class="space-y-2">
          <div>
            <label for="name" class="block">Name</label>
            <input v-model="newSchool.name" type="text" id="name" class="w-full p-2 border rounded">
          </div>

          <div>
            <button type="submit" class="px-4 py-2 text-white bg-blue-500 rounded-lg">Add School</button>
          </div>
        </div>
      </form>
    </div>
  </div>

</template>
