// global route middleware. we can remove the global from the name and use "auth" in definePageMeta.middleware[]
export default defineNuxtRouteMiddleware((to, from) => {
  if (to.path.includes("profile")) {
    const user = useSupabaseUser();
    if (user.value) {
      return;
    }
    return navigateTo("/login");
  }
});
