// src/libs/session.ts

export interface EmpleadoSesion {
  id: number;
  nombre: string;
  apellido: string;
  cargo: string;
}

const SESSION_KEY = "wallpawawqi_session";

export function setSession(empleado: EmpleadoSesion): void {
  localStorage.setItem(SESSION_KEY, JSON.stringify(empleado));
}

export function getSession(): EmpleadoSesion | null {
  const raw = localStorage.getItem(SESSION_KEY);
  if (!raw) return null;
  try {
    return JSON.parse(raw) as EmpleadoSesion;
  } catch {
    return null;
  }
}

export function clearSession(): void {
  localStorage.removeItem(SESSION_KEY);
}

export function isLoggedIn(): boolean {
  return getSession() !== null;
}

export function irConSesion(destino: string): void {
  if (isLoggedIn()) {
    window.location.href = destino;
  } else {
    window.location.href = `/login?redirect=${encodeURIComponent(destino)}`;
  }
}

export function logout(redirectTo: string = "/login"): void {
  clearSession();
  window.location.href = redirectTo;
}
