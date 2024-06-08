import {HttpHandlerFn, HttpInterceptorFn, HttpRequest} from "@angular/common/http";

export const outgoingTokenInterceptorInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next:
  HttpHandlerFn) => {
  const token = typeof window !== 'undefined' ? localStorage.getItem('token') : null;
  if (token !== null) {
  const modifiedReq = req.clone({
    headers: req.headers.set('Authorization', `Bearer ${token}`),
  });
  return next(modifiedReq);
}
 else return next(req);
};
