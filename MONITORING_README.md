
https://github.com/prometheus-operator/kube-prometheus

kubectl --namespace monitoring port-forward svc/prometheus-k8s 9090
kubectl --namespace monitoring port-forward svc/grafana 3000

graphana pass: pass123