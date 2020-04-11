package lets.vote.generalelection;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class PartyWebInfoFragment extends Fragment {
    static PartyWebInfoFragment instance;
    private WebView infoWebView; // 웹뷰 선언
    private WebSettings mWebSettings; //웹뷰세팅


    public PartyWebInfoFragment() {
        // Required empty public constructor
    }

    public static PartyWebInfoFragment getInstance() {
        if (instance == null) {
            instance = new PartyWebInfoFragment();
        }
        return instance;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_candidate_web_info, container, false);
        String keyword = getArguments().getString("keyword");
        ViewPager viewPager = getActivity().findViewById(R.id.viewPager);
        viewPager.setPadding(
                0,
                getArguments().getInt("top"),
                0,
                getArguments().getInt("bottom"));
        viewPager.requestLayout();

        TextView titleTextView = rootView.findViewById(R.id.navigationTitleTextView);
        titleTextView.setText("정당 정보 위키");

        infoWebView = rootView.findViewById(R.id.infoWebView);

        infoWebView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
        mWebSettings = infoWebView.getSettings(); //세부 세팅 등록
        mWebSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
        mWebSettings.setSupportMultipleWindows(true); // 새창 띄우기 허용 여부
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.setLoadWithOverviewMode(false); // 메타태그 허용 여부
        mWebSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(false); // 화면 줌 허용 여부
        mWebSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
        mWebSettings.setDomStorageEnabled(false); // 로컬저장소 허용 여부
        infoWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView newWebView = new WebView(getContext());
                WebView.WebViewTransport transport
                        = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();

                newWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
//                        browserIntent.setData(Uri.parse(url));
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        return true;
                    }
                });

                return true;
            }
        });

        String fullKeyword = keyword;

        if ("코리아".equals(keyword)) {
            fullKeyword = "가자코리아";
        } else if ("새벽당".equals(keyword)) {
            fullKeyword = "자유의새벽당";
        } else if ("자영업당".equals(keyword)) {
            fullKeyword = "중소자영업당";
        }

        infoWebView.loadUrl("https://namu.wiki/w/"+fullKeyword); // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작

        return rootView;
    }

    @Override
    public void onDetach() {
        ViewPager viewPager = getActivity().findViewById(R.id.viewPager);
        viewPager.setPadding(
                getArguments().getInt("left"),
                getArguments().getInt("top"),
                getArguments().getInt("right"),
                getArguments().getInt("bottom"));
        viewPager.requestLayout();
        super.onDetach();
    }
}
