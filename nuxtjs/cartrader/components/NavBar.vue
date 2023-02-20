<script setup>
const user = useSupabaseUser();
const supabase = useSupabaseClient();

const logout = async () => {
  // Make user.value = null and remove JWT from cookies
  const { error } = supabase.auth.signOut();
  console.log(user);
  if (error) {
    console.log(error);
  }
  // Old way of manually logging out when library fails
  // try {
  //   await $fetch("/api/_supabase/session", {
  //     method: "POST",
  //     body: {
  //       event: "SIGNED_OUT", session: null,
  //     }
  //   });
  // } catch (error) {
  //   console.log(error);
  // }
  // Navigate to home page
  navigateTo("/");
};
</script>

<template>
  <header
    class="sticky top-0 z-50 flex justify-between items-center space-x-1 border-b bg-white p-4 shadow-md"
  >
    <NuxtLink class="text-3xl font0mono" to="/">cartrader</NuxtLink>
    <div v-if="user" class="flex">
      <NuxtLink to="/profile/listings" class="mr-5">Profile</NuxtLink>
      <p @click="logout" class="cursor-pointer">Logout</p>
    </div>
    <NuxtLink v-else to="/login">Login</NuxtLink>
  </header>
</template>
