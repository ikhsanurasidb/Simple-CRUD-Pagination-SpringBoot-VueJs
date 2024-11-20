<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'

interface Student {
  firstName: string,
  lastName: string,
  email: string,
  schoolId: number,
}

interface PaginationResponse {
  content: Student[],
  empty: boolean,
  first: boolean,
  last: boolean,
  number: number,
  numberOfElements: number,
  pageable: {
    offset: number,
    pageNumber: number,
    pageSize: number,
    paged: boolean,
    sort: {
      empty: boolean,
      sorted: boolean,
      unsorted: boolean,
    },
    unpaged: boolean,
  },
  size: number,
  sort: {
    empty: boolean,
    sorted: boolean,
    unsorted: boolean,
  },
  totalElements: number,
  totalPages: number,
}

interface School {
  id: number,
  name: string,
}

const students = ref([] as Student[])
const schools = ref([] as School[])
const newStudent = ref<Student>({
  firstName: '',
  lastName: '',
  email: '',
  schoolId: 0,
})

onMounted(async () => {
  await fetchStudents()
  await fetchSchools()
})

const currentPage = ref(0)
const totalPages = ref(0)

async function fetchStudents(page = 0) {
  try {
    const response = await axios.get<PaginationResponse>(`http://localhost:8080/students?page=${page}`)
    students.value = response.data.content
    currentPage.value = response.data.pageable.pageNumber
    totalPages.value = response.data.totalPages
  } catch (error) {
    console.error('Failed to fetch students:', error)
  }
}

function changePage(newPage: number) {
  if (newPage >= 0 && newPage < totalPages.value) {
    fetchStudents(newPage)
  }
}

async function addStudent() {
  try {
    const response = await axios.post<Student>('http://localhost:8080/students', newStudent.value)
    students.value.push(response.data)
    newStudent.value = { firstName: '', lastName: '', email: '', schoolId: 0 }
  } catch (error) {
    console.error('Failed to add student:', error)
  }
}

async function fetchSchools() {
  try {
    const response = await axios.get<School[]>('http://localhost:8080/schools')
    schools.value = response.data
  } catch (error) {
    console.error('Failed to fetch schools:', error)
  }
}

const searchQuery = ref('')

async function searchStudents() {
  try {
    if (searchQuery.value.trim() === '') {
      await fetchStudents()
      return
    }

    const response = await axios.get<Student[]>(
      `http://localhost:8080/students/search/${encodeURIComponent(searchQuery.value)}`
    )
    students.value = response.data
  } catch (error) {
    console.error('Failed to search students:', error)
  }
}
</script>

<template>
  <div class="m-4 space-y-6">
    <div>
      <h3 class="text-2xl font-semibold">Students</h3>
    </div>

    <!-- Search -->
    <div>
      <label class="block text-sm font-medium">Filter Students</label>
      <input v-model="searchQuery" @input="searchStudents" type="text" class="w-full p-2 border rounded"
        placeholder="Search by first name" />
    </div>

    <!-- List of Students -->
    <div class="p-4 border rounded">
      <ul class="h-[75dvh] space-y-2 overflow-auto">
        <li v-for="(student, index) in students" :key="index" style="list-style-type: none"
          class="p-4 bg-white border rounded-lg shadow-md hover:bg-gray-50">
          <p class="">
            {{ index + 1 }}. {{ student.firstName }} {{ student.lastName }} - {{ student.email }}
          </p>
        </li>
      </ul>

      <div class="flex items-center justify-center mt-4 space-x-2">
        <button @click="changePage(currentPage - 1)" :disabled="currentPage === 0"
          class="px-3 py-1 text-white bg-blue-500 rounded hover:bg-blue-600 disabled:bg-gray-300">
          Previous
        </button>

        <span v-for="page in totalPages" :key="page" @click="changePage(page - 1)"
          :class="{ 'font-bold': page - 1 === currentPage }"
          class="px-3 py-1 bg-gray-200 rounded cursor-pointer hover:bg-gray-300">
          {{ page }}
        </span>

        <button @click="changePage(currentPage + 1)" :disabled="currentPage === totalPages - 1"
          class="px-3 py-1 text-white bg-blue-500 rounded hover:bg-blue-600 disabled:bg-gray-300">
          Next
        </button>
      </div>
    </div>

    <!-- Form -->
    <div class="p-4 bg-gray-100 border rounded-lg">
      <h4 class="mb-4 text-lg font-medium">Add Student</h4>
      <form @submit.prevent="addStudent" class="space-y-4">
        <div>
          <label class="block text-sm font-medium">First Name</label>
          <input v-model="newStudent.firstName" type="text" class="w-full p-2 border rounded" required />
        </div>
        <div>
          <label class="block text-sm font-medium">Last Name</label>
          <input v-model="newStudent.lastName" type="text" class="w-full p-2 border rounded" required />
        </div>
        <div>
          <label class="block text-sm font-medium">Email</label>
          <input v-model="newStudent.email" type="email" class="w-full p-2 border rounded" />
        </div>
        <div>
          <label class="block text-sm font-medium">School ID</label>
          <select v-model.number="newStudent.schoolId" class="w-full p-2 border rounded" required>
            <option v-for="(school, index) in schools" :key="index" :value="school.id">
              {{ school.name }}
            </option>
          </select>
        </div>
        <button type="submit" class="px-4 py-2 text-white bg-blue-500 rounded hover:bg-blue-600">
          Add Student
        </button>
      </form>
    </div>
  </div>
</template>
